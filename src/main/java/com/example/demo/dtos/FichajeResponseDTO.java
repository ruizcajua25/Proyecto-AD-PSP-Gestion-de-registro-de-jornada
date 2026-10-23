package com.example.demo.dtos;

import com.example.demo.entities.Fichaje;
import com.example.demo.entities.TipoFichaje;

import java.time.LocalDateTime;

public record FichajeResponseDTO(
        Long id,
        LocalDateTime momento,
        TipoFichaje tipo
) {

    public static FichajeResponseDTO from(Fichaje f) {
        return new FichajeResponseDTO(f.getId(), f.getMomento(), f.getTipo());
    }
}
