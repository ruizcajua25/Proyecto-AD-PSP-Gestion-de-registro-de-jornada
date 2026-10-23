package com.example.demo.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PresupuestoSuficienteValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PresupuestoSuficiente {

    String message() default "{presupuesto.insuficiente}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
