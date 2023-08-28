package com.medicalclinic.api.models.request;

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
public class DoctorRequest implements Serializable {
    private String name;
    private String licence;
    private String dni;
    private String tel;
    private String email;
    private List<String> specialities;
}
