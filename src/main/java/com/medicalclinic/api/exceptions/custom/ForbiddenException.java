package com.medicalclinic.api.exceptions.custom;

/**
 * @author claudio.vilas
 * date 08/2023
 */

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}
