package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.ProfesorService;
import com.ipn.mx.celex.application.dto.ProfesorDTO;
import com.ipn.mx.celex.application.mapper.ProfesorMapper;
import com.ipn.mx.celex.domain.entities.Profesor;
import com.ipn.mx.celex.domain.repository.ProfesorRepository;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository repository;

    public ProfesorServiceImpl(ProfesorRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfesorDTO> findAll() {
        return repository.findAll().stream().map(ProfesorMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfesorDTO findById(Long id) {
        return ProfesorMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional
    public ProfesorDTO save(ProfesorDTO dto) {
        Profesor saved = repository.save(ProfesorMapper.toEntity(dto));
        return ProfesorMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public ProfesorDTO update(Long id, ProfesorDTO dto) {
        Profesor existing = getEntity(id);
        existing.setNumEmpleado(dto.getNumEmpleado());
        existing.setNombre(dto.getNombre());
        existing.setApellidos(dto.getApellidos());
        existing.setIdiomaEspecialidad(dto.getIdiomaEspecialidad());
        return ProfesorMapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Profesor no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private Profesor getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + id));
    }
}
