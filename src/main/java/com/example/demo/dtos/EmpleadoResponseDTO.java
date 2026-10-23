package com.example.demo.dtos;

import com.example.demo.entities.Empleado;

import java.math.BigDecimal;

public record EmpleadoResponseDTO(
        Long id,
        String nombreCompleto,
        String cargo,
        BigDecimal salario,
        DepartamentoResumenDTO departamento
) {

    public static EmpleadoResponseDTO from(Empleado e) {
        DepartamentoResumenDTO depto = e.getDepartamento() == null
                ? null
                : new DepartamentoResumenDTO(e.getDepartamento().getId(), e.getDepartamento().getNombre());
        return new EmpleadoResponseDTO(e.getId(), e.getNombreCompleto(), e.getCargo(), e.getSalario(), depto);
    }
}
