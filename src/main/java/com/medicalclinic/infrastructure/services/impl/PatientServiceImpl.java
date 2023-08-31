package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.exceptions.custom.BadRequestException;
import com.medicalclinic.api.exceptions.custom.NotFoundException;
import com.medicalclinic.api.models.request.PatientRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.api.models.response.PatientResponse;
import com.medicalclinic.api.models.response.SocialWorkResponse;
import com.medicalclinic.domain.entity.ClinicHistoryEntity;
import com.medicalclinic.domain.entity.ClinicalEntity;
import com.medicalclinic.domain.entity.PatientEntity;
import com.medicalclinic.domain.entity.SocialWorkEntity;
import com.medicalclinic.domain.repository.ClinicalHistoryRepository;
import com.medicalclinic.domain.repository.ClinicalRepository;
import com.medicalclinic.domain.repository.PatientRepository;
import com.medicalclinic.domain.repository.SocialWorkRepository;
import com.medicalclinic.infrastructure.services.contract.PatientService;
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
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ClinicalRepository  clinicalRepository;
    private final SocialWorkRepository socialWorkRepository;
    private final ClinicalHistoryRepository clinicalHistoryRepository;


    @Override
    public PatientResponse create(PatientRequest request) {
        log.info("---> inicio servicio alta de pacientes");
        log.info("---> validando datos...");
        this.validatePatient(request);
        log.info("---> guardando paciente...");
        log.info("---> asignando clinica...");
        var clinical = clinicalRepository.findById(request.getClinicalId())
                .orElseThrow(()->{
                    log.error("ERROR: no existe la clinica con id {}", request.getClinicalId());
                    return new NotFoundException("no existe la clinica con id " + request.getClinicalId());
                });
        log.info("---> asignando obra social...");
        var socialWok = socialWorkRepository.findById(request.getSocialWorkId())
                .orElseThrow(()-> {
                    log.error("ERROR: no existe la obra social con id {}", request.getSocialWorkId());
                    return new NotFoundException("no existe la obra social con id " + request.getSocialWorkId());
                });
        var patient = PatientEntity.builder()
                .fullName(request.getFullName())
                .dni(request.getDni())
                .tel(request.getTel())
                .email(request.getEmail())
                .clinical(clinical)
                .socialWork(socialWok)
                .build();
        var response = patientRepository.save(patient);
        log.info("---> creando historia clinica...");
        var clinicalHistory = ClinicHistoryEntity.builder()
                .patient(response)
                .build();
        clinicalHistoryRepository.save(clinicalHistory);
        log.info("---> actualizando historia clinica de cliente");
        response.setClinicalHistory(clinicalHistory);
        patientRepository.save(response);
        log.info("---> finalizado servicio alta de paciente");
        return this.mapToDto(response);
    }

    @Override
    public PatientResponse read(Long id) {
        log.info("---> inicio servicio buscar paciente por id");
        log.info("---> buscando paciente id {}", id);
        var patient = patientRepository.findById(id).orElseThrow(()-> {
            log.error("ERROR: no existe el paciente con id {}", id);
            return new NotFoundException("no existe el paciente con id " + id);
        });
        log.info("finalizado servicio buscar paciento por id");
        return this.mapToDto(patient);
    }

    @Override
    public PatientResponse update(Long aLong, PatientRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("---> inicio servicio eliminar paciente por id");
        log.info("---> buscando paciente id {}", id);
        patientRepository.findById(id).orElseThrow(()-> {
            log.error("ERROR: no existe el paciente con id {}", id);
            return new NotFoundException("no existe el paciente con id " + id);
        });
        patientRepository.deleteById(id);
        log.info("---> finalizado servicio eliminar paciente por id");
    }

    @Override
    public PatientResponse getByFullName(String fullName) {
        log.info("---> inicio servicio buscar paciente por nombre");
        log.info("---> buscando paciente nombre {}", fullName);
        var patient = patientRepository.findByFullNameContaining(fullName)
                .orElseThrow(()-> {
                    log.error("ERROR: no existe el paciente con nombre {}", fullName);
                    return new NotFoundException("no existe el paciente con nombre " + fullName);
                });
        log.info("finalizado servicio buscar paciento por nombre");
        return this.mapToDto(patient);
    }

    @Override
    public Page<PatientResponse> getPandS(Integer page, Integer size, SortType order) {
        log.info("---> inicio servicio mostrar pacientes paginados y ordenados");
        PageRequest pr = null;
        switch (order){
            case NONE: pr = PageRequest.of(page, size);
                break;
            case UPPER: pr = PageRequest.of(page, size, Sort.by("fullName").descending());
                break;
            case LOWER: pr = PageRequest.of(page, size, Sort.by("fullName").ascending());
                break;
        }
        log.info("---> buscando pacientes...");
        log.info("---> finalizado servicio buscar pacientes paginado y ordenado");
        return patientRepository.findAll(pr).map(this::mapToDto);
    }

    private void validatePatient(PatientRequest patient){
        if (patient.getFullName().isEmpty()){
            log.error("ERROR: el nombre del paciente es requerido");
            throw new BadRequestException("el nombre del paciente es requerido");
        }

        if (patient.getDni().isEmpty()){
            log.error("ERROR: el dni del paciente es requerido");
            throw new BadRequestException("el dni del paciente es requerido");
        }
        if (patientRepository.existsByDni(patient.getDni())){
            log.error("ERROR: ya existe un paciente con dni {}", patient.getDni());
            throw new BadRequestException("ya existe un paciente con dni " + patient.getDni());
        }
        if (patient.getEmail().isEmpty()){
            log.error("ERROR: el email del cliente es requerido");
            throw new BadRequestException("el email del cliente es requerido");
        }
        if (patientRepository.existsByEmail(patient.getEmail())){
            log.error("ERROR: el email {} esta asignado a otro paciente", patient.getEmail());
            throw new BadRequestException("el email "+ patient.getEmail()+" esta asignado a otro paciente");
        }
        if (patient.getClinicalId() == null){
            log.error("ERROR: la clinica es requerida");
            throw new BadRequestException("la clinica es requerida");
        }
        clinicalRepository.findById(patient.getClinicalId()).orElseThrow(()-> {
            log.error("ERROR: no existe la clinica con id {}", patient.getClinicalId());
            return new NotFoundException("no existe la clinica con id " +patient.getClinicalId());
        });
        if (patient.getSocialWorkId() == null){
            log.error("ERROR: la obra social es requerida");
            throw new BadRequestException("la obra social es requerida");
        }
        socialWorkRepository.findById(patient.getSocialWorkId()).orElseThrow(()-> {
            log.error("ERROR: no existe la obra social con id {}", patient.getSocialWorkId());
            return new NotFoundException("no existe la obra social con id " +patient.getSocialWorkId());
        });

    }

    private PatientResponse mapToDto(PatientEntity patient){
        return PatientResponse.builder()
                .id(patient.getId())
                .fullName(patient.getFullName())
                .dni(patient.getDni())
                .tel(patient.getTel())
                .email(patient.getEmail())
                .clinical(this.mapClinical(patient.getClinical()))
                .socialWork(this.mapToSocialWork(patient.getSocialWork()))
                .SocialWorkMembership(patient.getSocialWorkMembership())
                .build();
    }

    private ClinicalResponse mapClinical(ClinicalEntity clinical){
        return ClinicalResponse.builder()
                .id(clinical.getId())
                .name(clinical.getName())
                .address(clinical.getAddress())
                .tel(clinical.getTel())
                .email(clinical.getEmail())
                .build();
    }

    private SocialWorkResponse mapToSocialWork(SocialWorkEntity socialWork){
        return SocialWorkResponse.builder()
                .id(socialWork.getId())
                .businessName(socialWork.getBusinessName())
                .build();
    }
}
