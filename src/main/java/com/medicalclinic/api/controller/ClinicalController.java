package com.medicalclinic.api.controller;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.infrastructure.services.contract.ClinicalService;
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
@RequestMapping("/api/v1/clinical")
@Slf4j
@Tag(name = "Clinica - Clinicas")
@RequiredArgsConstructor
public class ClinicalController {
    private final ClinicalService clinicalService;
    @PostMapping
    @Operation(summary = "servicio para alta de clinicas al sisteme",
            description = "servicio para alta de clinicas al sisteme")
    @Parameter(name = "request", description = "clinica DTO")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<ClinicalResponse> create(@Valid @RequestBody ClinicalRequest request){
        log.info("#### endpoint alta de clinicas ####");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicalService.create(request));
    }


    @GetMapping("/{id}")
    @Operation(summary = "servicio para consultar clinicas por id",
            description = "servicio para consultar clinicas por id si existe")
    @Parameter(name = "id", description = "id de clinica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<ClinicalResponse> read(@PathVariable Long id){
        log.info("#### endpoint de consulta de clinicas por id");
        return ResponseEntity.ok(clinicalService.read(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "servicio para modificar clinicas",
            description = "servicio paramodificar clinicas")
    @Parameters({
            @Parameter(name = "id", description = "id de la clinica"),
            @Parameter(name = "request", description = "datos de la clinica")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<ClinicalResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody ClinicalRequest request){
        log.info("#### endpoint modificar clinicas ####");
        return ResponseEntity.ok(clinicalService.update(id,request));
    }

    @GetMapping("/paginated-and-sorted")
    @Operation(summary = "servicio para ver clinicas paginado y ordenado",
            description = "servicio para ver clinicas paginado y ordenado")
    @Parameters({
            @Parameter(name = "page", description = "pagina a mostrar"),
            @Parameter(name = "size", description = "cantidad de elementos"),
            @Parameter(name = "sorted", description = "ordenacion")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "204", description = "no content")
    })
    public ResponseEntity<Page<ClinicalResponse>> pageAndSorted(@RequestParam Integer page,
                                                                @RequestParam Integer size,
                                                                @RequestParam SortType sorted){
        if (page < 0) page = 0;
        if (size <= 0) page = 5;
        log.info("#### endpoint mostrar clinicas paginado y ordenado");
        var response = clinicalService.viewPageAndSorted(page, size, sorted);
        if (response.getContent().isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "servicio para elinar clinicas por id",
               description = "servicio para elinar clinicas por id si existe")
    @Parameter(name = "id", description = "id de la clinica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("#### endpoint para eliminar clinicas por id");
        clinicalService.delete(id);
        return ResponseEntity.ok().build();
    }
}
