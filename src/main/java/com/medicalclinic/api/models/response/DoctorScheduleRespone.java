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
public class DoctorScheduleRespone implements Serializable {
    private Long id;
    private DoctorResponse doctor;
    private List<WorkDayResponse> workdays;
}
