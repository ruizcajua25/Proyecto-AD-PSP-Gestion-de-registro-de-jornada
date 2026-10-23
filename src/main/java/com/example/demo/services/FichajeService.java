package com.example.demo.services;

import com.example.demo.dtos.FichajeResponseDTO;
import com.example.demo.entities.Empleado;
import com.example.demo.entities.Fichaje;
import com.example.demo.entities.TipoFichaje;
import com.example.demo.errors.EmpleadoNoEncontradoException;
import com.example.demo.errors.FichajeDuplicadoException;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.FichajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FichajeService {

    private final FichajeRepository fichajeRepository;
    private final EmpleadoRepository empleadoRepository;

    public FichajeResponseDTO registrarFichaje(Long empleadoId, TipoFichaje tipoSolicitado) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(empleadoId));

        TipoFichaje ultimo = fichajeRepository.findTopByEmpleadoIdOrderByMomentoDesc(empleadoId)
                .map(Fichaje::getTipo)
                .orElse(TipoFichaje.SALIDA);
        TipoFichaje siguiente = ultimo == TipoFichaje.ENTRADA ? TipoFichaje.SALIDA : TipoFichaje.ENTRADA;

        if (tipoSolicitado != null && tipoSolicitado != siguiente) {
            throw new FichajeDuplicadoException(tipoSolicitado);
        }

        Fichaje fichaje = new Fichaje();
        fichaje.setEmpleado(empleado);
        fichaje.setMomento(LocalDateTime.now());
        fichaje.setTipo(siguiente);
        return FichajeResponseDTO.from(fichajeRepository.save(fichaje));
    }

    public List<FichajeResponseDTO> listarFichajesPorEmpleado(Long empleadoId) {
        if (!empleadoRepository.existsById(empleadoId)) {
            throw new EmpleadoNoEncontradoException(empleadoId);
        }
        return fichajeRepository.findByEmpleadoIdOrderByMomentoAsc(empleadoId).stream()
                .map(FichajeResponseDTO::from)
                .toList();
    }
}
