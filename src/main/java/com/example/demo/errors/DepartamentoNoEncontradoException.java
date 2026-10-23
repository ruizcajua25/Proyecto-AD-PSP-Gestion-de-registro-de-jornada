package com.example.demo.errors;

import lombok.Getter;

@Getter
public class DepartamentoNoEncontradoException extends RuntimeException {

    private final Long id;

    public DepartamentoNoEncontradoException(Long id) {
        this.id = id;
    }
}
