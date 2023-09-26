package com.medicalclinic.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkDayResponse {
    private Long id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
