package com.medicalclinic.infrastructure.services.contract;

import com.medicalclinic.api.models.request.DoctorRequest;
import com.medicalclinic.api.models.response.DoctorResponse;
import com.medicalclinic.util.enums.SortType;
import org.springframework.data.domain.Page;

/**
 * @author claudio.vilas
 * date 08/2023
 */
public interface DoctorService extends CrudService<DoctorRequest, DoctorResponse, Long>{
    Page<DoctorResponse> getPandO(Integer page, Integer size, SortType order);
}
