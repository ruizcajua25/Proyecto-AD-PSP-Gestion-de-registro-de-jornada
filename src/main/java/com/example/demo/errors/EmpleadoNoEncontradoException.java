package com.example.demo.errors;

import lombok.Getter;

@Getter
public class EmpleadoNoEncontradoException extends RuntimeException {

    private final Long id;

    public EmpleadoNoEncontradoException(Long id) {
        this.id = id;
    }
}
