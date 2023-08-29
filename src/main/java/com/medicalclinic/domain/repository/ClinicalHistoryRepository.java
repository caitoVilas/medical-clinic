package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.ClinicHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Repository
public interface ClinicalHistoryRepository extends JpaRepository<ClinicHistoryEntity, Long> {
}
