package com.example.demo.errors;

import com.example.demo.entities.TipoFichaje;

public class FichajeDuplicadoException extends RuntimeException {

    public FichajeDuplicadoException(TipoFichaje tipo) {
        super("El último fichaje ya es " + tipo + "; no se puede registrar otro " + tipo);
    }
}
