package com.medicalclinic.api.controller;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.api.models.response.SocialWorkResponse;
import com.medicalclinic.infrastructure.services.contract.SocialWorkService;
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
@RequestMapping("/api/v1/socialWork")
@Slf4j
@Tag(name = "Clinica - Obras Sociales")
@RequiredArgsConstructor
public class SocialWorkController {
    private final SocialWorkService socialWorkService;

    @PostMapping
    @Operation(summary = "servicio para alta de obras sociales al sisteme",
            description = "servicio para alta de obras sociales al sisteme")
    @Parameter(name = "request", description = "nombre obra social")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<SocialWorkResponse> create(@RequestParam String request){
        log.info("#### endpoint alta de obras sociales ####");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(socialWorkService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "servicio para consultar obras soiales por id",
            description = "servicio para consultar obras sociales por id si existe")
    @Parameter(name = "id", description = "id de la obra social")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<SocialWorkResponse> read(@PathVariable Long id){
        log.info("#### endpoint de consulta de obras sociales por id");
        return ResponseEntity.ok(socialWorkService.read(id));
    }

    @GetMapping("/paginated-and-sorted")
    @Operation(summary = "servicio para ver obras sociales paginado y ordenado",
            description = "servicio para ver obras sociales paginado y ordenado")
    @Parameters({
            @Parameter(name = "page", description = "pagina a mostrar"),
            @Parameter(name = "size", description = "cantidad de elementos"),
            @Parameter(name = "sorted", description = "ordenacion")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "204", description = "no content")
    })
    public ResponseEntity<Page<SocialWorkResponse>> pageAndSorted(@RequestParam Integer page,
                                                                @RequestParam Integer size,
                                                                @RequestParam SortType sorted){
        if (page < 0) page = 0;
        if (size <= 0) page = 5;
        log.info("#### endpoint mostrar oras sociales paginado y ordenado");
        var response = socialWorkService.getPandS(page, size, sorted);
        if (response.getContent().isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "servicio para elinar obras sociales por id",
            description = "servicio para elinar obras sociales por id si existe")
    @Parameter(name = "id", description = "id de la obra social")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("#### endpoint para eliminar obras sociales por id");
        socialWorkService.delete(id);
        return ResponseEntity.ok().build();
    }
}
