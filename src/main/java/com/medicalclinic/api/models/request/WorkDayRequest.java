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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkDayRequest implements Serializable {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
