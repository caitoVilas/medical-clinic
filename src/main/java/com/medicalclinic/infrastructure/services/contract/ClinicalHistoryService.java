package com.medicalclinic.infrastructure.services.contract;

import com.medicalclinic.api.models.response.ClinicalHistoryResponse;

public interface ClinicalHistoryService {
    ClinicalHistoryResponse viewClinicalHistories(Long patientId);

}
