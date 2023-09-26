package com.medicalclinic.api.controller;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.request.PatientRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.api.models.response.PatientResponse;
import com.medicalclinic.infrastructure.services.contract.PatientService;
import com.medicalclinic.util.enums.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@RestController
@RequestMapping("/api/v1/clinical/patients")
@Slf4j
@Tag(name = "Clinica - Pacientes")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    @Operation(summary = "servicio para alta de pacientes al sisteme",
            description = "servicio para alta de pacientes al sisteme")
    @Parameter(name = "request", description = "paciente DTO")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<PatientResponse> create(@RequestBody PatientRequest request){
        log.info("#### endpoint alta de pacientes ####");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "servicio para consultar pacientes por id",
            description = "servicio para consultar pacientes por id si existe")
    @Parameter(name = "id", description = "id del paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<PatientResponse> read(@PathVariable Long id){
        log.info("#### endpoint de consulta de paciente por id");
        return ResponseEntity.ok(patientService.read(id));
    }

    @GetMapping("/name/{fullName}")
    @Operation(summary = "servicio para consultar paciente por nombre",
            description = "servicio para consultar paciente por nombre si existe")
    @Parameter(name = "fullName", description = "id del paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<PatientResponse> getbyName(@PathVariable String fullName){
        log.info("#### endpoint de consulta de paciente por nombre");
        return ResponseEntity.ok(patientService.getByFullName(fullName));
    }

    @GetMapping("/paginated-and-sorted")
    @Operation(summary = "servicio para ver pacientes paginado y ordenado",
            description = "servicio para ver pacientes paginado y ordenado")
    @Parameters({
            @Parameter(name = "page", description = "pagina a mostrar"),
            @Parameter(name = "size", description = "cantidad de elementos"),
            @Parameter(name = "sorted", description = "ordenacion")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "204", description = "no content")
    })
    public ResponseEntity<Page<PatientResponse>> pageAndSorted(@RequestParam Integer page,
                                                                @RequestParam Integer size,
                                                                @RequestParam SortType sorted){
        if (page < 0) page = 0;
        if (size <= 0) page = 5;
        log.info("#### endpoint mostrar pacientes paginado y ordenado");
        var response = patientService.getPandS(page, size, sorted);
        if (response.getContent().isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "servicio para elinar paciente por id",
            description = "servicio para elinar paciente por id si existe")
    @Parameter(name = "id", description = "id del paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("#### endpoint para eliminar pacientes por id");
        patientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
