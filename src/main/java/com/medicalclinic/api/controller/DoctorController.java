package com.medicalclinic.api.controller;

import com.medicalclinic.api.models.request.DoctorRequest;
import com.medicalclinic.api.models.request.UserChangeRequest;
import com.medicalclinic.api.models.request.UserRequest;
import com.medicalclinic.api.models.response.DoctorResponse;
import com.medicalclinic.api.models.response.UserResponse;
import com.medicalclinic.infrastructure.services.contract.DoctorService;
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

/**
 * @author claudio.vilas
 * date 08/2023
 */

@RestController
@RequestMapping("/api/v1/doctors")
@Slf4j
@Tag(name = "Clinica - Medicos")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;


    @PostMapping
    @Operation(summary = "servicio para alta de medicos al sisteme",
            description = "servicio para alta de medicos al sisteme")
    @Parameter(name = "request", description = "datos del medico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<DoctorResponse> create(@RequestBody DoctorRequest request){
        log.info("#### endpoint alta de medicos ####");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "servicio para consultar medicos por id",
            description = "servicio para consultar medicos por id si existe")
    @Parameter(name = "id", description = "id del medico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<DoctorResponse> read(@PathVariable Long id){
        log.info("#### endpoint de consulta de medico por id");
        return ResponseEntity.ok(doctorService.read(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "servicio para modificar medico",
            description = "servicio par amodificar medico")
    @Parameters({
            @Parameter(name = "id", description = "id del medico"),
            @Parameter(name = "request", description = "datos del medico")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<DoctorResponse> update(@PathVariable Long id,
                                               @RequestBody DoctorRequest request){
        log.info("#### endpoint modificar medico ####");
        return ResponseEntity.ok(doctorService.update(id,request));
    }

    @GetMapping("/paginated-and-sorted")
    @Operation(summary = "servicio mostrar los medicos paginados y ordenados",
            description = "servicio mostrar los medicos paginados y ordenados")
    @Parameters({
            @Parameter(name = "page", description = "pagina a mostrar"),
            @Parameter(name = "size", description = "cantidad de elementos"),
            @Parameter(name = "sorted", description = "ordenacion")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "204", description = "no content")
    })
    public ResponseEntity<Page<DoctorResponse>> pageAndSorted(@RequestParam Integer page,
                                                            @RequestParam Integer size,
                                                            @RequestParam SortType sorted){
        if (page < 0) page = 0;
        if (size <= 0) page = 5;
        log.info("#### endpoint mostrar medicos paginado y ordenado");
        var response = doctorService.getPandO(page, size, sorted);
        if (response.getContent().isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "servicio para elinar medico por id",
            description = "servicio para elinar medico por id si existe")
    @Parameter(name = "id", description = "id del medico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("#### endpoint para eliminar medico por id");
        doctorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
