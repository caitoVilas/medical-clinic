package com.medicalclinic.infrastructure.services.contract;

import com.medicalclinic.api.models.request.DoctorScheduleRequest;
import com.medicalclinic.api.models.response.DoctorScheduleRespone;

/**
 * @author claudio.vilas
 * date 08/20223
 */
public interface DoctorScheduleService {

    DoctorScheduleRespone create(DoctorScheduleRequest request);
    DoctorScheduleRespone read(Long doctorId);
}
