package com.medicalclinic.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PatientRequest implements Serializable {
    private String fullName;
    private String dni;
    private String tel;
    private String email;
    private Long clinicalId;
    private Long socialWorkId;

}
