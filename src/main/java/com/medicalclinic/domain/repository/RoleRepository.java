package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.Role;
import com.medicalclinic.util.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author claudio.vilas
 * date. 08/2023
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleName roleName);
}
