package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.ClinicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author claudio.vilas
 * date: 08/2023
 */

@Repository
public interface ClinicalRepository extends JpaRepository<ClinicalEntity, Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    @Query("SELECT c FROM ClinicalEntity c WHERE c.id <> :id and c.name = :name")
    ClinicalEntity clinicalForName(Long id, String name);
    @Query("SELECT c FROM ClinicalEntity c WHERE c.id <> :id AND c.email = :email")
    ClinicalEntity clinicalForEmail(Long id, String email);
}
