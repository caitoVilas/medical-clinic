package com.medicalclinic.infrastructure.services.contract;

import com.medicalclinic.api.models.request.PatientRequest;
import com.medicalclinic.api.models.response.PatientResponse;
import com.medicalclinic.util.enums.SortType;
import org.springframework.data.domain.Page;

/**
 * @author claudio.vilas
 * date 08/2023
 */

public interface PatientService extends CrudService<PatientRequest, PatientResponse, Long>{
    PatientResponse getByFullName(String fullName);
    Page<PatientResponse> getPandS(Integer page, Integer size, SortType order);
}
