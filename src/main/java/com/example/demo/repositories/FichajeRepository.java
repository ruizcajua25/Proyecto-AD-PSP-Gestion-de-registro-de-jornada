package com.example.demo.repositories;

import com.example.demo.entities.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FichajeRepository extends JpaRepository<Fichaje, Long> {
    Optional<Fichaje> findTopByEmpleadoIdOrderByMomentoDesc(Long empleadoId);
    List<Fichaje> findByEmpleadoIdOrderByMomentoAsc(Long empleadoId);
}
