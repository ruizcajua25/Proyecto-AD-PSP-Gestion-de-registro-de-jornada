package com.example.demo.dtos;

import com.example.demo.entities.Departamento;

import java.math.BigDecimal;

public record DepartamentoResponseDTO(
        Long id,
        String nombre,
        BigDecimal presupuesto
) {

    public static DepartamentoResponseDTO from(Departamento d) {
        return new DepartamentoResponseDTO(d.getId(), d.getNombre(), d.getPresupuesto());
    }
}
