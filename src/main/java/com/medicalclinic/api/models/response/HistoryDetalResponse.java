package com.medicalclinic.api.models.response;

import com.medicalclinic.domain.repository.ClinicalRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HistoryDetalResponse implements Serializable {
    private Long id;
    private LocalDate date;
    private String history;
    private DoctorResponse doctor;
}
