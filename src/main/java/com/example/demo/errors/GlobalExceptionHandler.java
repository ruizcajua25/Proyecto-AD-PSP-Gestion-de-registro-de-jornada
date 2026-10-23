package com.example.demo.errors;

\import com.example.demo.dtos.ErrorValidacionDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<ErrorValidacionDTO> errores = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorValidacionDTO(error.getField(), error.getDefaultMessage()))
                .toList();
        return handleExceptionInternal(ex, validacionFallida(errores), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail validacion(ConstraintViolationException ex) {
        List<ErrorValidacionDTO> errores = ex.getConstraintViolations().stream()
                .map(violacion -> new ErrorValidacionDTO(
                        violacion.getPropertyPath().toString(),
                        violacion.getMessage()))
                .toList();
        return validacionFallida(errores);
    }

    private ProblemDetail validacionFallida(List<ErrorValidacionDTO> errores) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                mensaje("validacion.fallida"));
        problema.setProperty("errores", errores);
        return problema;
    }

    private String mensaje(String clave, Object... argumentos) {
        return messageSource.getMessage(clave, argumentos, LocaleContextHolder.getLocale());
    }
}
