package com.example.demo.errors;

public class EmpleadoNoEncontradoException extends RuntimeException {

    public EmpleadoNoEncontradoException(Long id) {
        super("No existe el empleado con id " + id);
    }
}
