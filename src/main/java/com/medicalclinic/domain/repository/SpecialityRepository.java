package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.SpecialityEntity;
import com.medicalclinic.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Repository
public interface SpecialityRepository extends JpaRepository<SpecialityEntity, Long> {
    SpecialityEntity findByName(String name);
    boolean existsByName(String name);
}
