package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.exceptions.custom.NotFoundException;
import com.medicalclinic.api.models.response.ClinicalHistoryResponse;
import com.medicalclinic.api.models.response.DoctorResponse;
import com.medicalclinic.api.models.response.HistoryDetalResponse;
import com.medicalclinic.api.models.response.SpecialityResponse;
import com.medicalclinic.domain.entity.ClinicHistoryEntity;
import com.medicalclinic.domain.entity.DoctorEntity;
import com.medicalclinic.domain.entity.HistoryDetailEntity;
import com.medicalclinic.domain.entity.SpecialityEntity;
import com.medicalclinic.domain.repository.PatientRepository;
import com.medicalclinic.infrastructure.services.contract.ClinicalHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ClinicalHistoryServiceImpl implements ClinicalHistoryService {
    private final PatientRepository patientRepository;


    @Override
    public ClinicalHistoryResponse viewClinicalHistories(Long patientId) {
        log.info("---> inicio servicio consultar historias clinicas por paciente");
        log.info("---> buscar paciente con id {}", patientId);
        var patient = patientRepository.findById(patientId).orElseThrow(()-> {
            log.error("ERROR: no se encuentra paciente con id {}", patientId);
            return new NotFoundException("no se encuentra paciente con id "+ patientId);
        });
        log.info("---> finalizado servicio consultar historias clinicas por paciente");
       var clinicalHistory = patient.getClinicalHistory();
        return mapToDto(clinicalHistory);
    }

   private ClinicalHistoryResponse mapToDto(ClinicHistoryEntity ce){
        return ClinicalHistoryResponse.builder()
                .id(ce.getId())
                .patient(ce.getPatient().getFullName())
                .details(ce.getDetails().stream().map(this::mapToDetail).collect(Collectors.toList()))
                .build();

   }

   private HistoryDetalResponse mapToDetail(HistoryDetailEntity detail){
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
