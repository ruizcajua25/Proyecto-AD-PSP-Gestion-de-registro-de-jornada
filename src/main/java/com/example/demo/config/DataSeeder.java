package com.example.demo.config;

import com.example.demo.entities.Departamento;
import com.example.demo.entities.Empleado;
import com.example.demo.entities.Fichaje;
import com.example.demo.entities.TipoFichaje;
import com.example.demo.repositories.DepartamentoRepository;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.FichajeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final FichajeRepository fichajeRepository;

    @PostConstruct
    public void initData() {
        if (departamentoRepository.count() > 0) {
            return;
        }

        Departamento informatica = departamento("Informática", "10000.00");
        Departamento rrhh = departamento("Recursos Humanos", "5000.00");
        Departamento ventas = departamento("Ventas", "8000.00");
        departamento("Marketing", "3000.00");

        Empleado ana = empleado("Ana Pérez García", "Desarrolladora", "2200.00", informatica);
        Empleado carlos = empleado("Carlos Romero Díaz", "Administrador de sistemas", "2000.00", informatica);
        Empleado lucia = empleado("Lucía Martín Sánchez", "Jefa de proyecto", "2800.00", informatica);
        empleado("Javier Gómez López", "Técnico de selección", "1700.00", rrhh);
        empleado("Marta Ruiz Fernández", "Responsable de nóminas", "1900.00", rrhh);
        Empleado pablo = empleado("Pablo Navarro Gil", "Comercial", "1600.00", ventas);
        empleado("Elena Torres Molina", "Jefa de ventas", "2500.00", ventas);
        empleado("Sergio Castro Ortega", "Becario", "900.00", null);

        fichaje(ana, "2026-10-21T08:00", TipoFichaje.ENTRADA);
        fichaje(ana, "2026-10-21T15:00", TipoFichaje.SALIDA);
        fichaje(ana, "2026-10-22T08:05", TipoFichaje.ENTRADA);
        fichaje(ana, "2026-10-22T15:10", TipoFichaje.SALIDA);
        fichaje(ana, "2026-10-23T07:58", TipoFichaje.ENTRADA);

        fichaje(carlos, "2026-10-22T09:00", TipoFichaje.ENTRADA);
        fichaje(carlos, "2026-10-22T17:00", TipoFichaje.SALIDA);

        fichaje(lucia, "2026-10-23T08:30", TipoFichaje.ENTRADA);

        fichaje(pablo, "2026-10-22T08:00", TipoFichaje.ENTRADA);
        fichaje(pablo, "2026-10-22T14:00", TipoFichaje.SALIDA);
    }

    private Departamento departamento(String nombre, String presupuesto) {
        return departamentoRepository.save(Departamento.builder()
                .nombre(nombre)
                .presupuesto(new BigDecimal(presupuesto))
                .build());
    }

    private Empleado empleado(String nombreCompleto, String cargo, String salario, Departamento departamento) {
        return empleadoRepository.save(Empleado.builder()
                .nombreCompleto(nombreCompleto)
                .cargo(cargo)
                .salario(new BigDecimal(salario))
                .departamento(departamento)
                .build());
    }

    private void fichaje(Empleado empleado, String momento, TipoFichaje tipo) {
        fichajeRepository.save(Fichaje.builder()
                .empleado(empleado)
                .momento(LocalDateTime.parse(momento))
                .tipo(tipo)
                .build());
    }
}
