package com.example.demo.controllers;

import com.example.demo.dtos.DepartamentoRequestDTO;
import com.example.demo.dtos.DepartamentoResponseDTO;
import com.example.demo.services.DepartamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public List<DepartamentoResponseDTO> listarTodos() {
        return departamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public DepartamentoResponseDTO buscarPorId(@PathVariable Long id) {
        return departamentoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartamentoResponseDTO crearDepartamento(@Valid @RequestBody DepartamentoRequestDTO dto) {
        return departamentoService.crearDepartamento(dto);
    }

    @PutMapping("/{id}")
    public DepartamentoResponseDTO actualizarDepartamento(@PathVariable Long id,
                                                          @Valid @RequestBody DepartamentoRequestDTO dto) {
        return departamentoService.actualizarDepartamento(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarDepartamento(@PathVariable Long id) {
        departamentoService.eliminarDepartamento(id);
    }
}
