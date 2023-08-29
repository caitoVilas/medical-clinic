package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.DoctorEntity;
import com.medicalclinic.domain.entity.PatientEntity;
import com.medicalclinic.domain.entity.SocialWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    Optional<PatientEntity> findByFullNameContaining(String fullName);
    @Query("SELECT p FROM PatientEntity p WHERE p.id <> :id AND p.dni = :dni")
    PatientEntity patienForDni(Long id, String dni);
    @Query("SELECT p FROM PatientEntity p WHERE p.id <> :id AND p.email = :email")
    PatientEntity patienForEmail(Long id, String email);
}
