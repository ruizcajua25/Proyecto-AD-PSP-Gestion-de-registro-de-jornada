package com.example.demo.services;

import com.example.demo.dtos.DepartamentoRequestDTO;
import com.example.demo.dtos.DepartamentoResponseDTO;
import com.example.demo.entities.Departamento;
import com.example.demo.errors.DepartamentoNoEncontradoException;
import com.example.demo.repositories.DepartamentoRepository;
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
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final Validator validator;

    public List<DepartamentoResponseDTO> listarTodos() {
        return departamentoRepository.findAll().stream()
                .map(DepartamentoResponseDTO::from)
                .toList();
    }

    public DepartamentoResponseDTO buscarPorId(Long id) {
        return DepartamentoResponseDTO.from(buscarEntidadPorId(id));
    }

    public DepartamentoResponseDTO crearDepartamento(DepartamentoRequestDTO dto) {
        Departamento depto = new Departamento();
        depto.setNombre(dto.nombre());
        depto.setPresupuesto(dto.presupuesto());
        return DepartamentoResponseDTO.from(departamentoRepository.save(depto));
    }

    public DepartamentoResponseDTO actualizarDepartamento(Long id, DepartamentoRequestDTO dto) {
        Departamento depto = buscarEntidadPorId(id);
        depto.setNombre(dto.nombre());
        depto.setPresupuesto(dto.presupuesto());
        validar(depto);
        return DepartamentoResponseDTO.from(depto);
    }

    public void eliminarDepartamento(Long id) {
        Departamento depto = buscarEntidadPorId(id);
        depto.getEmpleados().forEach(e -> e.setDepartamento(null));
        departamentoRepository.delete(depto);
    }

    Departamento buscarEntidadPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNoEncontradoException(id));
    }

    private void validar(Departamento depto) {
        Set<ConstraintViolation<Departamento>> violaciones = validator.validate(depto);
        if (!violaciones.isEmpty()) {
            throw new ConstraintViolationException(violaciones);
        }
    }
}
