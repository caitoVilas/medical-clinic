package com.medicalclinic.api.controller;

import com.medicalclinic.api.models.request.ClinicalRequest;
import com.medicalclinic.api.models.request.UserChangeRequest;
import com.medicalclinic.api.models.request.UserRequest;
import com.medicalclinic.api.models.response.ClinicalResponse;
import com.medicalclinic.api.models.response.UserResponse;
import com.medicalclinic.infrastructure.services.contract.UserService;
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
 * date 07/2023
 */

@RestController
@RequestMapping("/api/v1/clinical/users")
@Slf4j
@Tag(name = "Clinica - Usuarios")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "servicio para alta de usuarios al sisteme",
            description = "servicio para alta de usuarios al sisteme")
    @Parameter(name = "request", description = "datos del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request){
        log.info("#### endpoint alta de usuarios ####");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "servicio para consultar usuario por id",
            description = "servicio para consultar usuario por id si existe")
    @Parameter(name = "id", description = "id de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<UserResponse> read(@PathVariable Long id){
        log.info("#### endpoint de consulta de usuario por id");
        return ResponseEntity.ok(userService.read(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "servicio para modificar usuario",
            description = "servicio par amodificar usuario")
    @Parameters({
            @Parameter(name = "id", description = "id de usuario"),
            @Parameter(name = "request", description = "datos del usuario")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                                   @RequestBody UserChangeRequest request){
        log.info("#### endpoint modificar usuario ####");
        return ResponseEntity.ok(userService.update(id,request));
    }

    @GetMapping("/paginated-and-sorted")
    @Operation(summary = "servicio mostrar los usuarios paginados y ordenados",
            description = "servicio mostrar los usuarios paginados y ordenados")
    @Parameters({
            @Parameter(name = "page", description = "pagina a mostrar"),
            @Parameter(name = "size", description = "cantidad de elementos"),
            @Parameter(name = "sorted", description = "ordenacion")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "204", description = "no content")
    })
    public ResponseEntity<Page<UserResponse>> pageAndSorted(@RequestParam Integer page,
                                                                @RequestParam Integer size,
                                                                @RequestParam SortType sorted){
        if (page < 0) page = 0;
        if (size <= 0) page = 5;
        log.info("#### endpoint mostrar usuarios paginado y ordenado");
        var response = userService.getPageAndOrder(page, size, sorted);
        if (response.getContent().isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "servicio para elinar usuario por id",
               description = "servicio para elinar usuario por id si existe")
    @Parameter(name = "id", description = "id del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("#### endpoint para eliminar usuario por id");
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
