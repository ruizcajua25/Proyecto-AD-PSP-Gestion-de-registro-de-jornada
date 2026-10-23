package com.example.demo.errors;

public class DepartamentoNoEncontradoException extends RuntimeException {

    public DepartamentoNoEncontradoException(Long id) {
        super("No existe el departamento con id " + id);
    }
}
