package com.medicalclinic.api.models.request;

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
public class HistoryDetailRequest implements Serializable {
    private LocalDate date;
    private String history;
    private Long clinicalHistory;
    private Long doctor;
}
