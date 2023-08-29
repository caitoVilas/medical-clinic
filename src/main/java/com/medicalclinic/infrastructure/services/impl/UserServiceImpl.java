package com.medicalclinic.infrastructure.services.impl;

import com.medicalclinic.api.exceptions.custom.BadRequestException;
import com.medicalclinic.api.exceptions.custom.NotFoundException;
import com.medicalclinic.api.models.request.RoleRequest;
import com.medicalclinic.api.models.request.UserChangeRequest;
import com.medicalclinic.api.models.request.UserRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.api.models.response.RoleResponse;
import com.medicalclinic.api.models.response.UserResponse;
import com.medicalclinic.domain.entity.ClinicalEntity;
import com.medicalclinic.domain.entity.Role;
import com.medicalclinic.domain.entity.UserEntity;
import com.medicalclinic.domain.repository.ClinicalRepository;
import com.medicalclinic.domain.repository.RoleRepository;
import com.medicalclinic.domain.repository.UserRepository;
import com.medicalclinic.infrastructure.services.contract.UserService;
import com.medicalclinic.util.enums.RoleName;
import com.medicalclinic.util.enums.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author claudio.vilas
 * date 07/2023
 */

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClinicalRepository clinicalRepository;


    @Override
    public UserResponse create(UserRequest request) {
        log.info("---> inicio servicio alta de usuario");
        log.info("---> validando entradas...");
        if (userRepository.existsByUsername(request.getUsername())){
            log.error("ERROR: el alias {} ya esta registrado", request.getUsername());
            throw new BadRequestException("el alias "+ request.getUsername()+ " ya esta registrado");
        }
        if (userRepository.existsByDni(request.getDni())){
            log.error("ERROR: el dni {} ya esta registrado", request.getDni());
            throw new BadRequestException("el dni "+ request.getDni()+ " ya esta registrado");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            log.error("ERROR: el email {} ya esta registrado", request.getEmail());
            throw new BadRequestException("el email "+ request.getEmail()+" ya esta registrado");
        }
        var clinical = clinicalRepository.findById(request.getClinicalId())
                .orElseThrow(()-> {
                    log.error("ERROR: no existe la clinica con id {}", request.getClinicalId());
                    return new NotFoundException("no existe la clinica con id "+ request.getClinicalId());
                });
        var roles = new HashSet<Role>();
        RoleName name = null;
        for (String roleName: request.getRoles()){

            if (roleName.equals("ROLE_ADMIN")) name = RoleName.ROLE_ADMIN;
            if (roleName.equals("ROLE_USER")) name = RoleName.ROLE_USER;
            if (roleName.equals("ROLE_DOCTOR")) name = RoleName.ROLE_DOCTOR;
            var rol = roleRepository.findByRoleName(name);
            roles.add(rol);
        }
        log.info("---> guardando usuario...");
        var user = UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .tel(request.getTel())
                .dni(request.getDni())
                .email(request.getEmail())
                .clinical(clinical)
                .roles(roles)
                .build();
        var response = userRepository.save(user);
        return mapToDto(user);

    }

    @Override
    public UserResponse read(Long id) {
        log.info("---> inicio servicio buscar usario por id");
        log.info("---> buscando usuario con id {}", id);
        var user = userRepository.findById(id).orElseThrow(()-> {
            log.error("ERROR: no existe un usuario con id {}", id);
            return new NotFoundException("no existe un usuario con id "+ id);
        });
        log.info("---> finalizado servicio buscar usuario por id");
        return this.mapToDto(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("---> inicio servicio eliminar usuario por id");
        log.info("---> buscando usuario por id {}", id);
        userRepository.findById(id).orElseThrow(()-> {
            log.error("ERROR: no existe un usuario con id {}", id);
            return new NotFoundException("no existe un usuario con id "+ id);
        });
        userRepository.deleteById(id);
        log.info("finalizsado servicio eliminar id");
    }

    @Override
    public Page<UserResponse> getPageAndOrder(Integer page, Integer size, SortType order) {
        log.info("---> inicio servicio mostrar usuarios paginados y ordenados");
        PageRequest pr = null;
        switch (order){
            case NONE: pr = PageRequest.of(page, size);
            break;
            case UPPER: pr = PageRequest.of(page, size, Sort.by("fullName").descending());
            break;
            case LOWER: pr = PageRequest.of(page, size, Sort.by("fullName").ascending());
            break;
        }
        log.info("---> buscando usuarios...");
        log.info("---> finalizado servicio buscar usuarios paginado y ordenado");
        return userRepository.findAll(pr).map(this::mapToDto);
    }

    @Override
    public UserResponse update(Long id, UserChangeRequest request) {
        log.info("---> inicio servicio actulaizar usuario");
        log.info("---> buscando usuario id {}",id);
        var user = userRepository.findById(id).orElseThrow(()-> {
            log.error("ERROR: no existe un usuario con id {}", id);
            return new NotFoundException("no existe un usuario con id "+ id);
        });
        log.info("---> iniciando validaciones...");
        if (userRepository.userForUsername(id, request.getUsername()) != null){
            log.error("ERROR: ya existe otro usuario con el alias {}", request.getUsername());
            throw new BadRequestException("ya existe otro usuario con el alias "+ request.getUsername());
        }
        if (userRepository.userForDni(id,request.getDni()) != null){
            log.error("ERROR: ya existe otro usuario con el dni {}", request.getDni());
            throw new BadRequestException("ya existe otro usuario con el dni "+ request.getDni());
        }
        if (userRepository.userForEmail(id, request.getEmail()) != null){
            log.error("ERROR: ya existe otro usuario con el email {}", request.getEmail());
            throw new BadRequestException("ya existe otro usuario con el email "+ request.getEmail());
        }
        log.info("---> guardando actualizacion...");
        if (!user.getUsername().isEmpty()) user.setUsername(request.getUsername());
        if (!user.getFullName().isEmpty()) user.setFullName(request.getFullName());
        if (!user.getTel().isEmpty()) user.setTel(request.getTel());
        if (!user.getDni().isEmpty()) user.setDni(request.getDni());
        if (!user.getEmail().isEmpty()) user.setDni(request.getDni());
        userRepository.save(user);
        log.info("---> finalizado servicio actualizar usuario");
        return this.mapToDto(user);
    }

    private UserResponse mapToDto(UserEntity user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .tel(user.getTel())
                .dni(user.getDni())
                .clinical(this.mapToClinical(user.getClinical()))
                .roles(user.getRoles().stream().map(this::mapToRole).collect(Collectors.toSet()))
                .build();
    }

    private ClinicalResponse mapToClinical(ClinicalEntity clinical){
        return ClinicalResponse.builder()
                .id(clinical.getId())
                .name(clinical.getName())
                .address(clinical.getAddress())
                .tel(clinical.getTel())
                .email(clinical.getEmail())
                .build();
    }

    private RoleResponse mapToRole(Role role){
        return RoleResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

}
