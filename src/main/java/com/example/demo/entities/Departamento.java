package com.example.demo.entities;


import com.example.demo.validations.PresupuestoSuficiente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PresupuestoSuficiente
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private BigDecimal presupuesto;

    @OneToMany(mappedBy = "departamento")
    @Builder.Default
    private Set<Empleado> empleados = new HashSet<>();
}
