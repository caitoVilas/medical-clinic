package com.medicalclinic.infrastructure.services.contract;

import com.medicalclinic.api.models.request.UserChangeRequest;
import com.medicalclinic.api.models.request.UserRequest;
import com.medicalclinic.api.models.response.UserResponse;
import com.medicalclinic.util.enums.SortType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

/**
 * @author claudio.vilas
 * date 07/2023
 */

public interface UserService extends CrudService<UserRequest, UserResponse,Long> {
    Page<UserResponse> getPageAndOrder(Integer page, Integer size, SortType order);
    UserResponse update(Long id, UserChangeRequest request);
}
