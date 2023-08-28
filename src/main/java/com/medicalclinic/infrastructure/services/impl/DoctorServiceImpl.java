package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.models.request.DoctorRequest;
import com.medicalclinic.api.models.response.DoctorResponse;
import com.medicalclinic.api.models.response.SpecialityResponse;
import com.medicalclinic.domain.entity.DoctorEntity;
import com.medicalclinic.domain.entity.SpecialityEntity;
import com.medicalclinic.domain.repository.DoctorRepository;
import com.medicalclinic.domain.repository.SpecialityRepository;
import com.medicalclinic.infrastructure.services.contract.DoctorService;
import com.medicalclinic.util.enums.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final SpecialityRepository specialityRepository;


    @Override
    public DoctorResponse create(DoctorRequest request) {
        log.info("---> inicio servicio alta de medicos");
        log.info("---> validando datos...");
        this.validateDoctor(request);
        log.info("---> guardando doctor...");
        var specialities = new ArrayList<SpecialityEntity>();
        request.getSpecialities().forEach(sp -> {
            var spelist = specialityRepository.findByName(sp);
            specialities.add(spelist);
        });
        var doctor = DoctorEntity.builder()
                .name(request.getName())
                .licence(request.getLicence())
                .dni(request.getDni())
                .tel(request.getTel())
                .email(request.getEmail())
                .specialities(specialities)
                .build();
        var response = doctorRepository.save(doctor);
        return this.mapToDto(response);
    }

    @Override
    public DoctorResponse read(Long id) {
        log.info("---> inicio servicio buscar medico por id");
        log.info("---> buscando medico con id {}", id);
        var doctor = doctorRepository.findById(id).orElseThrow();
        log.info("---> finalizado servicio buscar medico por id");
        return this.mapToDto(doctor);
    }

    @Override
    public DoctorResponse update(Long id, DoctorRequest request) {
        log.info("---> inicio servicio modificare medico");
        log.info("---> buscando medico con id {}", id);
        var doctor = doctorRepository.findById(id).orElseThrow();
        if (!request.getName().isEmpty()) doctor.setName(request.getName());
        log.info("---> validando datos...");
        if (!request.getLicence().isEmpty()){
            if (doctorRepository.doctorForLicence(id, request.getLicence()) != null){
                log.error("ERROR: licencia {} pertenece a otro medico", request.getLicence());
                throw new RuntimeException();
            }else {
                doctor.setLicence(request.getLicence());
            }
        }
        if (!request.getDni().isEmpty()){
            if (doctorRepository.doctorForDni(id, request.getDni()) != null){
                log.error("ERROR: el dni {} pertenece a otro medico", request.getLicence());
                throw new RuntimeException();
            }else {
                doctor.setDni(request.getDni());
            }
        }
        if (!request.getEmail().isEmpty()){
            if (doctorRepository.doctorForEmail(id, request.getEmail()) != null){
                log.error("ERROR: el email {} pertenece a otro medico", request.getEmail());
                throw new RuntimeException();
            }else {
                doctor.setEmail(request.getEmail());
            }
        }
        log.info("---> guardando cambios...");
        doctorRepository.save(doctor);
        return this.mapToDto(doctor);
    }

    @Override
    public void delete(Long id) {
        log.info("---> inicio servicio eliminar medico por id");
        log.info("---> uscando medico por id {}", id);
        doctorRepository.findById(id).orElseThrow();
        log.info("---> finalizado servicio eliminar medico por id");
        doctorRepository.deleteById(id);
    }

    @Override
    public Page<DoctorResponse> getPandO(Integer page, Integer size, SortType order) {
        log.info("---> inicio servicio mostrar medicos paginados y ordenados");
        PageRequest pr = null;
        switch (order){
            case NONE: pr = PageRequest.of(page, size);
                break;
            case UPPER: pr = PageRequest.of(page, size, Sort.by("name").descending());
                break;
            case LOWER: pr = PageRequest.of(page, size, Sort.by("name").ascending());
                break;
        }
        log.info("---> buscando medicos...");
        log.info("---> finalizado servicio buscar medicos paginado y ordenado");
        return doctorRepository.findAll(pr).map(this::mapToDto);
    }

    private void validateDoctor(DoctorRequest doctor){
        if (doctor.getName().isEmpty()){
            log.error("ERROR: el nombre del medico es requerido");
            throw new RuntimeException();
        }

        if (doctor.getLicence().isEmpty()){
            log.error("ERROR: la licenciae del medico es requerido");
            throw new RuntimeException();
        }

        if (doctor.getDni().isEmpty()){
            log.error("ERROR: el dni del medico es requerido");
            throw new RuntimeException();
        }

        if (doctor.getEmail().isEmpty()){
            log.error("ERROR: el email del medico es requerido");
            throw new RuntimeException();
        }
        if (doctorRepository.existsByLicence(doctor.getLicence())){
            log.error("ERROR: la licencia {} esta asignada a otro medico", doctor.getLicence());
            throw new RuntimeException();
        }

        if (doctorRepository.existsByDni(doctor.getDni())){
            log.error("ERROR: el dni {} esta asignado a otro medico", doctor.getDni());
            throw new RuntimeException();
        }

        if (doctorRepository.existsByEmail(doctor.getEmail())){
            log.error("ERROR: el email {} esta asignado a otro medico", doctor.getEmail());
            throw new RuntimeException();
        }

        if (doctor.getSpecialities().isEmpty()){
            log.error("ERROR: no se asigno especialidad");
            throw new RuntimeException();
        }

        doctor.getSpecialities().forEach(sp -> {
            if (!specialityRepository.existsByName(sp)){
                log.error("ERROR: no existe la especialidad {}", sp);
                throw new RuntimeException();
            }
        });
    }

    private DoctorResponse mapToDto(DoctorEntity doctor) {
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
