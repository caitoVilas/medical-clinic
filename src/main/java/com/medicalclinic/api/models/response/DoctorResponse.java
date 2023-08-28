package com.medicalclinic.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DoctorResponse implements Serializable {
    private Long id;
    private String name;
    private String licence;
    private String dni;
    private String tel;
    private String email;
    private List<SpecialityResponse> especialities;
}
