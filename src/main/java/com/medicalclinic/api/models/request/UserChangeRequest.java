package com.medicalclinic.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author claudio.vilas
 * date 07/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserChangeRequest implements Serializable {
    private String username;
    private String fullName;
    private String tel;
    private String dni;
    private String email;
}
