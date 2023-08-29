package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.models.response.SocialWorkResponse;
import com.medicalclinic.domain.entity.SocialWorkEntity;
import com.medicalclinic.domain.repository.SocialWorkRepository;
import com.medicalclinic.infrastructure.services.contract.SocialWorkService;
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
public class SocialWorkServiceImpl implements SocialWorkService {
    private final SocialWorkRepository socialWorkRepository;


    @Override
    public SocialWorkResponse create(String request) {
        log.info("---> inicio servicio alta de obra social");
        log.info("---> validando datos...");
        this.validateSocialWork(request);
        log.info("---> guardando obra social...");
        var socialWork = SocialWorkEntity.builder()
                .businessName(request)
                .build();
        var response = socialWorkRepository.save(socialWork);
        return this.mapToDto(response);
    }

    @Override
    public SocialWorkResponse read(Long id) {
        log.info("---> inicio servico buscar obra social por id");
        log.info("---> buscando obra social con id {}", id);
        var socialWork = socialWorkRepository.findById(id).orElseThrow();
        log.info("---> finalizado servicio buscar obra social por id");
        return this.mapToDto(socialWork);
    }

    @Override
    public SocialWorkResponse update(Long aLong, String request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("---> inicio servicio eliminar obra social por id");
        log.info("---> buscando obra social con id {}", id);
        socialWorkRepository.findById(id).orElseThrow();
        socialWorkRepository.deleteById(id);
        log.info("finalizado servicio eliminar obra social por id");
    }

    private void validateSocialWork(String businessName){
        if (businessName.isEmpty()){
            log.error("ERROR: la razon social es requerida");
            throw new RuntimeException();
        }

        if (socialWorkRepository.existsByBusinessName(businessName)){
            log.error("ERROR: la razon social {} ya esta asignada", businessName);
            throw new RuntimeException();
        }
    }

    private SocialWorkResponse mapToDto(SocialWorkEntity socialWork){
        return SocialWorkResponse.builder()
                .id(socialWork.getId())
                .businessName(socialWork.getBusinessName())
                .build();
    }

    @Override
    public Page<SocialWorkResponse> getPandS(Integer page, Integer size, SortType order) {
        log.info("---> inicio servicio mostrar obras sociales paginados y ordenados");
        PageRequest pr = null;
        switch (order){
            case NONE: pr = PageRequest.of(page, size);
                break;
            case UPPER: pr = PageRequest.of(page, size, Sort.by("businessName").descending());
                break;
            case LOWER: pr = PageRequest.of(page, size, Sort.by("businessName").ascending());
                break;
        }
        log.info("---> buscando obras sociales...");
        log.info("---> finalizado servicio buscar obras sociales paginado y ordenado");
        return socialWorkRepository.findAll(pr).map(this::mapToDto);
    }
}
