package com.medicalclinic.api.controller;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.request.HistoryDetailRequest;
import com.medicalclinic.api.models.response.ClinicalHistoryResponse;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.api.models.response.HistoryDetalResponse;
import com.medicalclinic.infrastructure.services.contract.ClinicalHistoryService;
import com.medicalclinic.infrastructure.services.contract.HistoryDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@RestController
@RequestMapping("/api/v1/clinical-history")
@Slf4j
@Tag(name = "Clinica - Historias Clinicas")
@RequiredArgsConstructor
public class ClinicalHistoryController {
    private final HistoryDetailService historyDetailService;
    private final ClinicalHistoryService clinicalHistoryService;

    @PostMapping
    @Operation(summary = "servicio para alta de detalle de historia clinica al sisteme",
            description = "servicio para alta de detalle de historia clinica al sisteme")
    @Parameter(name = "request", description = "detalle DTO")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<HistoryDetalResponse> create(@RequestBody HistoryDetailRequest request){
        log.info("#### endpoint alta de detalle de historia clinica ####");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(historyDetailService.create(request));
    }

    @GetMapping("/{patientId}")
    @Operation(summary = "servicio para consultar  historias clinicas por paciente",
            description = "servicio para consultar  historias clinicas por paciente")
    @Parameter(name = "patientId", description = "id del paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<ClinicalHistoryResponse> viewClinicalHistory(@PathVariable Long patientId){
        log.info("#### endpoint de consulta de historias clinicas por paciente");
        return ResponseEntity.ok(clinicalHistoryService.viewClinicalHistories(patientId));
    }
}
