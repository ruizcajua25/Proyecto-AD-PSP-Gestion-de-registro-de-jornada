package com.example.demo.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EmpleadoRequestDTO(
        @NotBlank String nombreCompleto,
        @NotBlank String cargo,
        @NotNull @DecimalMin("0.01") BigDecimal salario) {
}
