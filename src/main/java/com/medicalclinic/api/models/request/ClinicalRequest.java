package com.medicalclinic.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClinicalRequest implements Serializable {
    private String name;
    private String address;
    private String tel;
    @NotBlank(message = "el email es requerido")
    @Email(message = "el formato del email es invalido")
    private String email;
}
