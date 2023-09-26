package com.medicalclinic.api.controller;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.request.DoctorScheduleRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.api.models.response.DoctorScheduleRespone;
import com.medicalclinic.infrastructure.services.contract.DoctorScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Repository
@RequestMapping("/api/v1/clinical/schedule")
@Slf4j
@Tag(name = "Clinica - Agenda Medicos")
@RequiredArgsConstructor
public class DoctorScheduleController {
    private final DoctorScheduleService doctorScheduleService;

    @PostMapping
    @Operation(summary = "servicio para alta de agenda medicos al sisteme",
            description = "servicio para alta de agenda medicos al sisteme")
    @Parameter(name = "request", description = "datos agenda")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<DoctorScheduleRespone> create(@RequestBody DoctorScheduleRequest request){
        log.info("#### endpoint alta de agenda medicos ####");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorScheduleService.create(request));
    }

    @GetMapping("/{doctorId}")
    @Operation(summary = "servicio para consultar agendas por medico",
            description = "servicio para consultar agendas por medico si existe")
    @Parameter(name = "doctorId", description = "id del medico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<DoctorScheduleRespone> read(@PathVariable Long doctorId){
        log.info("#### endpoint de consulta de agendas por medico");
        return ResponseEntity.ok(doctorScheduleService.read(doctorId));
    }
}
