package com.example.demo.services;

import com.example.demo.dtos.EmpleadoRequestDTO;
import com.example.demo.dtos.EmpleadoResponseDTO;
import com.example.demo.entities.Departamento;
import com.example.demo.entities.Empleado;
import com.example.demo.errors.DepartamentoNoEncontradoException;
import com.example.demo.errors.EmpleadoNoEncontradoException;
import com.example.demo.repositories.DepartamentoRepository;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.FichajeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final FichajeRepository fichajeRepository;
    private final Validator validator;

    public List<EmpleadoResponseDTO> listarTodos() {
        return empleadoRepository.findAll().stream()
                .map(EmpleadoResponseDTO::from)
                .toList();
    }

    public EmpleadoResponseDTO buscarPorId(Long id) {
        return EmpleadoResponseDTO.from(buscarEntidadPorId(id));
    }

    public EmpleadoResponseDTO crearEmpleadoDTO(EmpleadoRequestDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setNombreCompleto(dto.nombreCompleto());
        empleado.setCargo(dto.cargo());
        empleado.setSalario(dto.salario());
        return EmpleadoResponseDTO.from(empleadoRepository.save(empleado));
    }

    public EmpleadoResponseDTO actualizarEmpleado(Long id, EmpleadoRequestDTO dto) {
        Empleado empleado = buscarEntidadPorId(id);
        empleado.setNombreCompleto(dto.nombreCompleto());
        empleado.setCargo(dto.cargo());
        empleado.setSalario(dto.salario());
        if (empleado.getDepartamento() != null) {
            validar(empleado.getDepartamento());
        }
        return EmpleadoResponseDTO.from(empleado);
    }

    public void eliminarEmpleado(Long id) {
        Empleado empleado = buscarEntidadPorId(id);
        fichajeRepository.deleteAll(fichajeRepository.findByEmpleadoIdOrderByMomentoAsc(id));
        empleadoRepository.delete(empleado);
    }

    public EmpleadoResponseDTO asignarDepartamento(Long empleadoId, Long deptoId) {
        Empleado empleado = buscarEntidadPorId(empleadoId);
        Departamento depto = departamentoRepository.findById(deptoId)
                .orElseThrow(() -> new DepartamentoNoEncontradoException(deptoId));

        if (empleado.getDepartamento() != null) {
            empleado.getDepartamento().getEmpleados().remove(empleado);
        }
        empleado.setDepartamento(depto);
        depto.getEmpleados().add(empleado);

        validar(depto);
        return EmpleadoResponseDTO.from(empleado);
    }

    Empleado buscarEntidadPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(id));
    }

    private void validar(Departamento depto) {
        Set<ConstraintViolation<Departamento>> violaciones = validator.validate(depto);
        if (!violaciones.isEmpty()) {
            throw new ConstraintViolationException(violaciones);
        }
    }
}
