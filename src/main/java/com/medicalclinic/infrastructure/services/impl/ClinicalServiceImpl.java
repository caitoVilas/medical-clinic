package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.domain.entity.ClinicalEntity;
import com.medicalclinic.domain.repository.ClinicalRepository;
import com.medicalclinic.infrastructure.services.contract.ClinicalService;
import com.medicalclinic.util.enums.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class ClinicalServiceImpl implements ClinicalService {
    private final ClinicalRepository clinicalRepository;


    @Override
    public Page<ClinicalResponse> viewPageAndSorted(Integer page, Integer size, SortType sortType) {
        log.info("---> inicio servicio ver clinicas paginado y ordenado");
        PageRequest pr = null;
        switch (sortType){
            case NONE: pr = PageRequest.of(page, size);
            break;
            case UPPER: pr = PageRequest.of(page,size, Sort.by("name").descending());
            break;
            case LOWER: pr = PageRequest.of(page, size, Sort.by("name").ascending());
            break;
        }
        log.info("---> buscando clinicas...");
        log.info("---> finalizado servicio buscar clinicas paginado y ordenado");
        return clinicalRepository.findAll(pr).map(this::mapToDto);
    }

    @Override
    public ClinicalResponse create(ClinicalRequest request) {
        log.info("---> inicio servicio crear clinica");
        log.info("---> validando datos...");
        if (request.getName().isEmpty()){
            log.error("ERROR: el nombre es requerido");
        }
        if (clinicalRepository.existsByName(request.getName())){
            log.error("ERROR: ya existe una clinica con nombre {}", request.getName());
            throw new RuntimeException();
        }
        if (clinicalRepository.existsByEmail(request.getEmail())){
            log.error("ERROR: el email {} ya esta registrado con otra clinica", request.getEmail());
            throw new RuntimeException();
        }
        log.info("---> guardando clinica...");
        var clinical = clinicalRepository.save(this.mapToEntity(request));
        log.info("---> finalizado servicio guardar clinica");
        return this.mapToDto(clinical);
    }

    @Override
    public ClinicalResponse read(Long id) {
        log.info("---> inicio buscar clinica por id");
        log.info("--->buscando clinica id {}", id);
        var clinical = clinicalRepository.findById(id).orElseThrow();
        log.info("finalizado servicio buscar clinica por id");
        return mapToDto(clinical);
    }

    @Override
    public ClinicalResponse update(Long id, ClinicalRequest request) {
        log.info("---> inicio servicio modificar clinica");
        log.info("---> buscando clinica con id {}", id);
        var clinacal = clinicalRepository.findById(id).orElseThrow();
        if (clinicalRepository.clinicalForName(id, request.getName()) != null){
            log.error("ERROR: ya existe una clinica con nombre {}", clinacal.getName());
            throw new RuntimeException();
        }
        if (clinicalRepository.clinicalForEmail(id, request.getEmail()) != null){
            log.error("ERROR: el email {} ya esta registrado con otra clinica", clinacal.getEmail());
            throw new RuntimeException();
        }
        log.info("---> actualizando clinica....");
        if (!request.getName().isEmpty()) clinacal.setName(request.getName());
        if (!request.getAddress().isEmpty()) clinacal.setAddress(request.getAddress());
        if (!request.getTel().isEmpty()) clinacal.setTel(request.getTel());
        if (!request.getEmail().isEmpty()) clinacal.setEmail(request.getEmail());
        log.info("---> guardando cambios...");
        clinicalRepository.save(clinacal);
        return this.mapToDto(clinacal);
    }

    @Override
    public void delete(Long id) {
        log.info("---> inicio servicio eliminar clinica");
        log.info("---> buscando clinica id {}", id);
        clinicalRepository.findById(id).orElseThrow();
        clinicalRepository.deleteById(id);
    }

    private ClinicalEntity mapToEntity(ClinicalRequest clinical){
        return ClinicalEntity.builder()
                .name(clinical.getName())
                .address(clinical.getAddress())
                .tel(clinical.getTel())
                .email(clinical.getEmail())
                .build();
    }

    private ClinicalResponse mapToDto(ClinicalEntity clinical){
        return ClinicalResponse.builder()
                .id(clinical.getId())
                .name(clinical.getName())
                .address(clinical.getAddress())
                .tel(clinical.getTel())
                .email(clinical.getEmail())
                .build();
    }
}
