package com.example.demo.controllers;

import com.example.demo.dtos.DepartamentoRequestDTO;
import com.example.demo.dtos.DepartamentoResponseDTO;
import com.example.demo.services.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Departamentos", description = "CRUD de departamentos")
@RestController
@RequestMapping("/api/v1/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @Operation(summary = "Listar todos los departamentos")
    @ApiResponse(responseCode = "200", description = "Lista de departamentos")
    @GetMapping
    public List<DepartamentoResponseDTO> listarTodos() {
        return departamentoService.listarTodos();
    }

    @Operation(summary = "Buscar un departamento por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento no encontrado")
    })
    @GetMapping("/{id}")
    public DepartamentoResponseDTO buscarPorId(@PathVariable Long id) {
        return departamentoService.buscarPorId(id);
    }

    @Operation(summary = "Crear un departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Departamento creado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "409", description = "Nombre de departamento repetido")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartamentoResponseDTO crearDepartamento(@Valid @RequestBody DepartamentoRequestDTO dto) {
        return departamentoService.crearDepartamento(dto);
    }

    @Operation(summary = "Actualizar un departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos o presupuesto insuficiente"),
            @ApiResponse(responseCode = "404", description = "Departamento no encontrado")
    })
    @PutMapping("/{id}")
    public DepartamentoResponseDTO actualizarDepartamento(@PathVariable Long id,
                                                          @Valid @RequestBody DepartamentoRequestDTO dto) {
        return departamentoService.actualizarDepartamento(id, dto);
    }

    @Operation(summary = "Eliminar un departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Departamento eliminado"),
            @ApiResponse(responseCode = "404", description = "Departamento no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarDepartamento(@PathVariable Long id) {
        departamentoService.eliminarDepartamento(id);
    }
}
