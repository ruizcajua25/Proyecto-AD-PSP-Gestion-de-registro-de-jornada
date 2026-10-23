package com.example.demo.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EmpleadoRequestDTO(
        @NotBlank(message = "{empleado.nombre.obligatorio}")
        String nombreCompleto,

        @NotBlank(message = "{empleado.cargo.obligatorio}")
        String cargo,

        @NotNull(message = "{empleado.salario.obligatorio}")
        @DecimalMin(value = "0.01", message = "{empleado.salario.minimo}")
        BigDecimal salario) {
}
