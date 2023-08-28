package com.medicalclinic.infrastructure.services.contract;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.util.enums.SortType;
import org.springframework.data.domain.Page;

/**
 * @author claudio.vilas
 * date: 08/2023
 */

public interface ClinicalService extends
        CrudService<ClinicalRequest, ClinicalResponse, Long>{
    Page<ClinicalResponse> viewPageAndSorted(Integer page, Integer size, SortType sortType);
}
