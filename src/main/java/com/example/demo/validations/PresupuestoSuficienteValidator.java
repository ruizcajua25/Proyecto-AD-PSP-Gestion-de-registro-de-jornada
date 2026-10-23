package com.example.demo.validations;

import com.example.demo.entities.Departamento;
import com.example.demo.entities.Empleado;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.Objects;

public class PresupuestoSuficienteValidator
        implements ConstraintValidator<PresupuestoSuficiente, Departamento> {

    @Override
    public boolean isValid(Departamento depto, ConstraintValidatorContext context) {
        if (depto == null || depto.getPresupuesto() == null || depto.getEmpleados() == null) {
            return true;
        }

        BigDecimal total = depto.getEmpleados().stream()
                .map(Empleado::getSalario)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.compareTo(depto.getPresupuesto()) <= 0;
    }
}
