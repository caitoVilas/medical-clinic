package com.medicalclinic.infrastructure.services.contract;

import com.medicalclinic.api.models.request.HistoryDetailRequest;
import com.medicalclinic.api.models.response.ClinicalHistoryResponse;
import com.medicalclinic.api.models.response.HistoryDetalResponse;

/**
 * @author claudio.vilas
 * date 08/2023
 */

public interface HistoryDetailService extends CrudService<HistoryDetailRequest, HistoryDetalResponse, Long> {
}
