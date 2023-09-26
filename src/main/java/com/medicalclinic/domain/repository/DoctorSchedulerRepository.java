package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.DoctorEntity;
import com.medicalclinic.domain.entity.DoctorScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Repository
public interface DoctorSchedulerRepository extends JpaRepository<DoctorScheduleEntity, Long> {
    Optional<DoctorScheduleEntity> findByDoctor(DoctorEntity doctor);
}
