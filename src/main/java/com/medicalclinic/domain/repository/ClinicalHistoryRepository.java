package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.ClinicHistoryEntity;
import com.medicalclinic.domain.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Repository
public interface ClinicalHistoryRepository extends JpaRepository<ClinicHistoryEntity, Long> {
    /*@Query(value = "SELECT * FROM clinical_histories WHERE patient_id = ?", nativeQuery = true)
    Object getByPatient(Long patientId);*/
    /*@Query("SELECT h FROM ClinicHistoryEntity h Where h.patient =:patient")
    Optional<ClinicHistoryEntity> getByPatient(PatientEntity patient);*/
    Optional<ClinicHistoryEntity> findByPatient(PatientEntity patient);
}
