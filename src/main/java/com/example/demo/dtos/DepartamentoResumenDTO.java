package com.example.demo.dtos;

import com.example.demo.entities.Departamento;

public record DepartamentoResumenDTO(
        Long id,
        String nombre
) {

    public static DepartamentoResumenDTO from(Departamento d) {
        return new DepartamentoResumenDTO(d.getId(), d.getNombre());
    }
}
