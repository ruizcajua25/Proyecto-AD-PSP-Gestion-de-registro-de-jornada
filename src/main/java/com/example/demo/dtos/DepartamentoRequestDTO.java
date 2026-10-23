package com.example.demo.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DepartamentoRequestDTO(
        @NotBlank(message = "{departamento.nombre.obligatorio}")
        @Size(max = 100, message = "{departamento.nombre.longitud}")
        String nombre,

        @NotNull(message = "{departamento.presupuesto.obligatorio}")
        @DecimalMin(value = "0.00", message = "{departamento.presupuesto.minimo}")
        BigDecimal presupuesto) {
}
