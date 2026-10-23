package com.example.demo.controllers;

import com.example.demo.dtos.EmpleadoRequestDTO;
import com.example.demo.dtos.EmpleadoResponseDTO;
import com.example.demo.dtos.FicharRequestDTO;
import com.example.demo.dtos.FichajeResponseDTO;
import com.example.demo.services.EmpleadoService;
import com.example.demo.services.FichajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final FichajeService fichajeService;

    @GetMapping
    public List<EmpleadoResponseDTO> listarTodos() {
        return empleadoService.listarTodos();
    }

    @GetMapping("/{id}")
    public EmpleadoResponseDTO buscarPorId(@PathVariable Long id) {
        return empleadoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoResponseDTO crearEmpleadoDTO(@Valid @RequestBody EmpleadoRequestDTO dto) {
        return empleadoService.crearEmpleadoDTO(dto);
    }

    @PutMapping("/{id}")
    public EmpleadoResponseDTO actualizarEmpleado(@PathVariable Long id,
                                                  @Valid @RequestBody EmpleadoRequestDTO dto) {
        return empleadoService.actualizarEmpleado(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
    }

    @PutMapping("/{empleadoId}/departamento/{deptoId}")
    public EmpleadoResponseDTO asignarDepartamento(@PathVariable Long empleadoId,
                                                   @PathVariable Long deptoId) {
        return empleadoService.asignarDepartamento(empleadoId, deptoId);
    }

    @PostMapping("/{empleadoId}/fichar")
    @ResponseStatus(HttpStatus.CREATED)
    public FichajeResponseDTO registrarFichaje(@PathVariable Long empleadoId,
                                               @RequestBody(required = false) FicharRequestDTO dto) {
        return fichajeService.registrarFichaje(empleadoId, dto == null ? null : dto.tipo());
    }

    @GetMapping("/{empleadoId}/fichajes")
    public List<FichajeResponseDTO> listarFichajesPorEmpleado(@PathVariable Long empleadoId) {
        return fichajeService.listarFichajesPorEmpleado(empleadoId);
    }
}
