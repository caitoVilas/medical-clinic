package com.medicalclinic.api.exceptions.custom;

/**
 * @author claudio.vilas
 * date 08/2023
 */

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
