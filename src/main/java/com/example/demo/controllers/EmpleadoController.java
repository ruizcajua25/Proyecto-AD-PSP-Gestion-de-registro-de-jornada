package com.example.demo.controllers;

import com.example.demo.dtos.EmpleadoRequestDTO;
import com.example.demo.dtos.EmpleadoResponseDTO;
import com.example.demo.dtos.FicharRequestDTO;
import com.example.demo.dtos.FichajeResponseDTO;
import com.example.demo.services.EmpleadoService;
import com.example.demo.services.FichajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Empleados", description = "CRUD de empleados, asignación a departamentos y fichajes")
@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final FichajeService fichajeService;

    @Operation(summary = "Listar todos los empleados")
    @ApiResponse(responseCode = "200", description = "Lista de empleados")
    @GetMapping
    public List<EmpleadoResponseDTO> listarTodos() {
        return empleadoService.listarTodos();
    }

    @Operation(summary = "Buscar un empleado por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @GetMapping("/{id}")
    public EmpleadoResponseDTO buscarPorId(@PathVariable Long id) {
        return empleadoService.buscarPorId(id);
    }

    @Operation(summary = "Crear un empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoResponseDTO crearEmpleadoDTO(@Valid @RequestBody EmpleadoRequestDTO dto) {
        return empleadoService.crearEmpleadoDTO(dto);
    }

    @Operation(summary = "Actualizar un empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos o presupuesto insuficiente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @PutMapping("/{id}")
    public EmpleadoResponseDTO actualizarEmpleado(@PathVariable Long id,
                                                  @Valid @RequestBody EmpleadoRequestDTO dto) {
        return empleadoService.actualizarEmpleado(id, dto);
    }

    @Operation(summary = "Eliminar un empleado y sus fichajes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empleado eliminado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
    }

    @Operation(summary = "Asignar un empleado a un departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado asignado"),
            @ApiResponse(responseCode = "400", description = "Presupuesto insuficiente para los salarios"),
            @ApiResponse(responseCode = "404", description = "Empleado o departamento no encontrado")
    })
    @PutMapping("/{empleadoId}/departamento/{deptoId}")
    public EmpleadoResponseDTO asignarDepartamento(@PathVariable Long empleadoId,
                                                   @PathVariable Long deptoId) {
        return empleadoService.asignarDepartamento(empleadoId, deptoId);
    }

    @Operation(summary = "Registrar un fichaje, alterna ENTRADA y SALIDA")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fichaje registrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "409", description = "El tipo indicado repite el último fichaje")
    })
    @PostMapping("/{empleadoId}/fichar")
    @ResponseStatus(HttpStatus.CREATED)
    public FichajeResponseDTO registrarFichaje(@PathVariable Long empleadoId,
                                               @RequestBody(required = false) FicharRequestDTO dto) {
        return fichajeService.registrarFichaje(empleadoId, dto == null ? null : dto.tipo());
    }

    @Operation(summary = "Historial de fichajes de un empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial ordenado por momento"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @GetMapping("/{empleadoId}/fichajes")
    public List<FichajeResponseDTO> listarFichajesPorEmpleado(@PathVariable Long empleadoId) {
        return fichajeService.listarFichajesPorEmpleado(empleadoId);
    }
}
