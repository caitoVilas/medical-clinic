package com.medicalclinic.api.exceptions.controller;

import com.medicalclinic.api.exceptions.custom.BadRequestException;
import com.medicalclinic.api.exceptions.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExceptionController {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> badRequestException(Exception e, HttpServletRequest request){
        var response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
