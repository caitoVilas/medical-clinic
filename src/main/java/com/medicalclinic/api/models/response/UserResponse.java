package com.medicalclinic.api.models.response;

import com.medicalclinic.util.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author claudio.vilas
 * date 07/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String username;
    private String fullName;
    private String tel;
    private String dni;
    private String email;
    private ClinicalResponse clinical;
    private Set<RoleResponse> roles;
}
