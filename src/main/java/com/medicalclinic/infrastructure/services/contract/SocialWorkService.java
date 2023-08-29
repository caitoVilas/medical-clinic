package com.medicalclinic.infrastructure.services.contract;

import com.medicalclinic.api.models.response.SocialWorkResponse;
import com.medicalclinic.util.enums.SortType;
import org.springframework.data.domain.Page;

/**
 * @author claudio.vilas
 * date 08/2023
 */

public interface SocialWorkService extends CrudService<String, SocialWorkResponse, Long>{
    Page<SocialWorkResponse> getPandS(Integer page, Integer size, SortType order);
}
