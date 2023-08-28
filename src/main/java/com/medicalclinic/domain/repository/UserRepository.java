package com.medicalclinic.domain.repository;

import com.medicalclinic.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author claudio.vilas
 * date: 08/2023
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    @Query("SELECT u FROM UserEntity u WHERE u.id <> :id AND u.username = :username")
    UserEntity userForUsername(Long id, String username);
    @Query("SELECT u FROM UserEntity u WHERE u.id <> :id AND u.dni = :dni")
    UserEntity userForDni(Long id, String dni);
    @Query("SELECT u FROM UserEntity u WHERE u.id <> :id AND u.email = :email")
    UserEntity userForEmail(Long id, String email);

}
