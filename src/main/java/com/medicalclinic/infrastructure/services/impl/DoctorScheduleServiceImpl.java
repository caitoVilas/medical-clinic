package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.exceptions.custom.BadRequestException;
import com.medicalclinic.api.exceptions.custom.NotFoundException;
import com.medicalclinic.api.models.request.DoctorScheduleRequest;
import com.medicalclinic.api.models.request.WorkDayRequest;
import com.medicalclinic.api.models.response.DoctorScheduleRespone;
import com.medicalclinic.domain.entity.DoctorScheduleEntity;
import com.medicalclinic.domain.entity.WorkdayEntity;
import com.medicalclinic.domain.repository.DoctorRepository;
import com.medicalclinic.domain.repository.DoctorSchedulerRepository;
import com.medicalclinic.domain.repository.WorkDayRepository;
import com.medicalclinic.infrastructure.services.contract.DoctorScheduleService;
import com.medicalclinic.util.map.MapUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
    private final DoctorSchedulerRepository doctorSchedulerRepository;
    private final WorkDayRepository workDayRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public DoctorScheduleRespone create(DoctorScheduleRequest request) {
        log.info("--> servicio creacion de agenda de medicos");
        log.info("---> validando entradas...");
        request.getWorkdays().forEach(this::validateSchedule);
        log.info("---> buscando medico...");
        var doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(()-> {
            log.error("ERROR: no se encuentra doctor con id {}", request.getDoctorId());
            return new NotFoundException("no se encuentra doctor con id "+ request.getDoctorId());
        });
        if (doctorSchedulerRepository.findByDoctor(doctor).isPresent()){
            log.error("ERROR: el doctor {} ya tiene agenda creada", doctor.getName());
            throw new BadRequestException("el doctor "+ doctor.getName()+ " ya tiene agenda creada");
        }
        log.info("---> guardando datos...");
        var workDays = new ArrayList<WorkdayEntity>();
        request.getWorkdays().forEach((wd) -> {
            var workDay = WorkdayEntity.builder()
                    .dayOfWeek(wd.getDayOfWeek())
                    .startTime(wd.getStartTime())
                    .endTime(wd.getEndTime())
                    .build();
            workDays.add(workDay);
        });
        var days = workDayRepository.saveAll(workDays);
        var doctorSchedule = DoctorScheduleEntity.builder()
                .doctor(doctor)
                .workDays(days)
                .build();
        var response = doctorSchedulerRepository.save(doctorSchedule);
        log.info("---> finalizado servicio creacion de agenda de medicos");
        return MapUtil.mapDoctorScheduleToDto(response);
    }

    @Override
    public DoctorScheduleRespone read(Long doctorId) {
        log.info("---> inicio servicio buscar agenda de medico por su id");
        log.info("---> buscando doctor con id {}", doctorId);
        var doctor = doctorRepository.findById(doctorId).orElseThrow(()-> {
            log.error("ERROR: no se encuentra doctor con id {}", doctorId);
            return new NotFoundException("no se encuentra doctor con id "+ doctorId);
        });
        log.info("---> buscando agenda del doctor {}", doctor.getName());
        var schedule = doctorSchedulerRepository.findByDoctor(doctor).orElseThrow(()-> {
            log.error("ERROR: no se encuentra agenda del doctor {}", doctor.getName());
            return new NotFoundException("no se encuentra agenda del doctor "+ doctor.getName());
        });
        log.info("---> finalizado servicio buscar agenda de medico por su id");
        return MapUtil.mapDoctorScheduleToDto(schedule);
    }


    private void validateSchedule(WorkDayRequest request){
        if (request.getDayOfWeek().isEmpty()){
            log.error("ERROR: el dia es requerido");
            throw new BadRequestException("el dia es requerido");
        }
        if (request.getStartTime().isEmpty()){
            log.error("ERROR: la hora de comienzo es requerida");
            throw new BadRequestException("la hora de comienzo es requerida");
        }
        if (request.getEndTime().isEmpty()){
            log.error("ERROR: la hora de finalizsacion es requerida");
            throw new BadRequestException("la hora de finalizacion es requerida");
        }
    }
}
