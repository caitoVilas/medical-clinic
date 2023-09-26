package com.medicalclinic.api.controller;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.api.models.response.SpecialityResponse;
import com.medicalclinic.infrastructure.services.contract.SpecialityService;
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
@RequestMapping("/api/v1/clinical/speciality")
@Slf4j
@Tag(name = "Clinica - Especialidades")
@RequiredArgsConstructor
public class SpecialityController {
    private final SpecialityService specialityService;

    @PostMapping
    @Operation(summary = "servicio para alta de especialidades al sisteme",
            description = "servicio para alta de especialidades al sisteme")
    @Parameter(name = "request", description = "nombre especialidad")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<SpecialityResponse> create(@RequestParam String request){
        log.info("#### endpoint alta de especialidades ####");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(specialityService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "servicio para consultar especialidades por id",
            description = "servicio para consultar especialidades por id si existe")
    @Parameter(name = "id", description = "id de especialidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<SpecialityResponse> read(@PathVariable Long id){
        log.info("#### endpoint de consulta de especialidad por id");
        return ResponseEntity.ok(specialityService.read(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "servicio para elinar especialidades por id",
            description = "servicio para elinar especialidades por id si existe")
    @Parameter(name = "id", description = "id de la especialidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("#### endpoint para eliminar especialidad por id");
        specialityService.delete(id);
        return ResponseEntity.ok().build();
    }
}
