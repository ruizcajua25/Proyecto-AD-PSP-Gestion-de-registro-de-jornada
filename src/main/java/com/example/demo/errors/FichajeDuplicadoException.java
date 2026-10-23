package com.example.demo.errors;

import com.example.demo.entities.TipoFichaje;
import lombok.Getter;

@Getter
public class FichajeDuplicadoException extends RuntimeException {

    private final TipoFichaje tipo;

    public FichajeDuplicadoException(TipoFichaje tipo) {
        this.tipo = tipo;
    }
}
