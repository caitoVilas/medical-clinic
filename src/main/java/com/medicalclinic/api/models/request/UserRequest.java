package com.medicalclinic.api.models.request;

import com.medicalclinic.util.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequest implements Serializable {
    private String username;
    private String password;
    private String fullName;
    private String tel;
    private String dni;
    private String email;
    private Long clinicalId;
    private Set<String> roles;
}
