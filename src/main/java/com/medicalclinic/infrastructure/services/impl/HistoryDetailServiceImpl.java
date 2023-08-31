package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.exceptions.custom.BadRequestException;
import com.medicalclinic.api.exceptions.custom.NotFoundException;
import com.medicalclinic.api.models.request.HistoryDetailRequest;
import com.medicalclinic.api.models.response.DoctorResponse;
import com.medicalclinic.api.models.response.HistoryDetalResponse;
import com.medicalclinic.api.models.response.SpecialityResponse;
import com.medicalclinic.domain.entity.DoctorEntity;
import com.medicalclinic.domain.entity.HistoryDetailEntity;
import com.medicalclinic.domain.entity.SpecialityEntity;
import com.medicalclinic.domain.repository.ClinicalHistoryRepository;
import com.medicalclinic.domain.repository.DoctorRepository;
import com.medicalclinic.domain.repository.HistoryDetailRepository;
import com.medicalclinic.infrastructure.services.contract.HistoryDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HistoryDetailServiceImpl implements HistoryDetailService {
    private final HistoryDetailRepository historyDetailRepository;
    private final ClinicalHistoryRepository clinicalHistoryRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public HistoryDetalResponse create(HistoryDetailRequest request) {
        log.info("---> inicio servicio guardar detalle historia clinica");
        log.info("---> validando entradas...");
        this.validateHistoryDetail(request);
        log.info("---> guardando detalle en historia clinica {}", request.getClinicalHistory());
        var clinicalHistory = clinicalHistoryRepository.findById(request.getClinicalHistory()).orElseThrow(()-> {
            log.error("ERROR: no se encuentra historia clinica con id {}", request.getClinicalHistory());
            return new NotFoundException("no se encuentra historia clinica con id "+ request.getClinicalHistory());
        });
        log.info("---> asignando medico...");
        var doctor = doctorRepository.findById(request.getDoctor()).orElseThrow(()-> {
            log.error("ERROR: no se encuentra medico con id {}", request.getDoctor());
            return new NotFoundException("no se encuentra medico con id "+ request.getDoctor());
        });
        var detail = HistoryDetailEntity.builder()
                .date(LocalDate.now())
                .history(request.getHistory())
                .clinicalHistory(clinicalHistory)
                .doctor(doctor)
                .build();
        var response = historyDetailRepository.save(detail);
        log.info("---> finalizado servicio guardar detalle de historia clinica");
        return this.mapTodto(response);
    }

    @Override
    public HistoryDetalResponse read(Long aLong) {
        return null;
    }

    @Override
    public HistoryDetalResponse update(Long aLong, HistoryDetailRequest request) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    private void validateHistoryDetail(HistoryDetailRequest request){
        if (request.getHistory().isEmpty()){
            log.error("ERROR: el detalle de la historia no tiene contenido");
            throw new  BadRequestException("el detalle de la historia no tiene contenido");
        }
        if (request.getClinicalHistory() == null){
            log.error("ERROR: no se especifica historia clinica para asignar");
            throw new BadRequestException("no se especifica historia clinica para asignar");
        }
        if (request.getDoctor() == null){
            log.error("ERROR: no se especifica medico para esta historia");
            throw new BadRequestException("no se especifica medico para esta historia");
        }
        clinicalHistoryRepository.findById(request.getClinicalHistory()).orElseThrow(()-> {
            log.error("ERROR: no se encuentra historia clinica con id {}", request.getClinicalHistory());
            return new NotFoundException("no se encuentra historia clinica con id "+ request.getClinicalHistory());
        });

        doctorRepository.findById(request.getDoctor()).orElseThrow(()-> {
            log.error("ERROR: no se encuentra medico con id {}", request.getDoctor());
            return new NotFoundException("no se encuentra medico con id "+ request.getDoctor());
        });
    }

    private HistoryDetalResponse mapTodto(HistoryDetailEntity detail){
        return HistoryDetalResponse.builder()
                .id(detail.getId())
                .date(detail.getDate())
                .history(detail.getHistory())
                .doctor(this.mapDoctor(detail.getDoctor()))
                .build();
    }

    private DoctorResponse mapDoctor(DoctorEntity doctor){
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .licence(doctor.getLicence())
                .dni(doctor.getDni())
                .tel(doctor.getTel())
                .email(doctor.getEmail())
                .especialities(doctor.getSpecialities()
                        .stream().map(this::mapSpecialist).collect(Collectors.toList()))
                .build();
    }

    private SpecialityResponse mapSpecialist(SpecialityEntity speciality){
        return SpecialityResponse.builder()
                .id(speciality.getId())
                .name(speciality.getName())
                .build();
    }
}
