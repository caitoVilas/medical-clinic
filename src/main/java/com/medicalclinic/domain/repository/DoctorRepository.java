package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.DoctorEntity;
import com.medicalclinic.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    boolean existsByLicence(String licence);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
    @Query("SELECT d FROM DoctorEntity d WHERE d.id <> :id AND d.licence = :licence")
    DoctorEntity doctorForLicence(Long id, String licence);
    @Query("SELECT d FROM DoctorEntity d WHERE d.id <> :id AND d.dni = :dni")
    DoctorEntity doctorForDni(Long id, String dni);
    @Query("SELECT d FROM DoctorEntity d WHERE d.id <> :id AND d.email = :email")
    DoctorEntity doctorForEmail(Long id, String email);
}
