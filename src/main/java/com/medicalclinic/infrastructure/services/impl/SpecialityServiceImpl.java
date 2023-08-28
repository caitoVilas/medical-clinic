package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.models.response.SpecialityResponse;
import com.medicalclinic.domain.entity.SpecialityEntity;
import com.medicalclinic.domain.repository.SpecialityRepository;
import com.medicalclinic.infrastructure.services.contract.SpecialityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {
    private final SpecialityRepository specialityRepository;


    @Override
    public SpecialityResponse create(String request) {
        log.info("---> inicio servicio guardar especialidad");
        log.info("---> validando datos");
        if (request.isEmpty()){
            log.error("ERROR: el nombre es requerido");
            throw new RuntimeException();
        }
        if (specialityRepository.existsByName(request)){
            log.error("ERROR: la especialidad {} ya exite", request);
            throw new RuntimeException();
        }
        log.info("---> guardando especialidad...");
        var speciality = SpecialityEntity.builder()
                .name(request).build();
        var response = specialityRepository.save(speciality);
        log.info("---> finalizado servicio guardar especialidad");
        return this.mapToDto(response);
    }

    @Override
    public SpecialityResponse read(Long id) {
        log.info("---> inicio servicio buscar especialidad por id");
        log.info("---> buscando especialidad id {}", id);
        var speciality = specialityRepository.findById(id).orElseThrow();
        log.info("---> finalizado servicio buscar especialidad por id");
        return this.mapToDto(speciality);
    }

    @Override
    public SpecialityResponse update(Long aLong, String request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("---> inicio servicio eliminar especialidad por id");
        log.info("---> buscando especialidad id {}", id);
        var speciality = specialityRepository.findById(id).orElseThrow();
        specialityRepository.deleteById(id);
    }

    private SpecialityResponse mapToDto(SpecialityEntity speciality){
        return SpecialityResponse.builder()
                .id(speciality.getId())
                .name(speciality.getName())
                .build();
    }
}
