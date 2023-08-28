package com.medicalclinic.api.models.response;

import com.medicalclinic.util.enums.RoleName;
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
public class RoleResponse implements Serializable {
    private Long id;
    private RoleName roleName;
}
