package com.example.demo.errors;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.context.i18n.LocaleContextHolder;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(DepartamentoNoEncontradoException.class)
    public ProblemDetail departamentoNoEncontrado(DepartamentoNoEncontradoException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                mensaje("departamento.no-encontrado", ex.getId()));
    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ProblemDetail empleadoNoEncontrado(EmpleadoNoEncontradoException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                mensaje("empleado.no-encontrado", ex.getId()));
    }

    @ExceptionHandler(FichajeDuplicadoException.class)
    public ProblemDetail fichajeDuplicado(FichajeDuplicadoException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                mensaje("fichaje.duplicado", ex.getTipo()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail integridad(DataIntegrityViolationException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                mensaje("integridad.datos"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail validacion(ConstraintViolationException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private String mensaje(String clave, Object... argumentos) {
        return messageSource.getMessage(clave, argumentos, LocaleContextHolder.getLocale());
    }
}
