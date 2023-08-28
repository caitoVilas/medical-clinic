package com.medicalclinic.infrastructure.services.contract;

/**
 * @author claudio.vilas
 * date: 08/2023
 */

public interface CrudService<RQ,RS,ID>{
    RS create(RQ request);
    RS read(ID id);
    RS update(ID id, RQ request);
    void delete(ID id);
}
