package com.medicalclinic.api.exceptions.custom;

/**
 * @author claudio.vilas
 * date 08/2023
 */

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
