package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.AlumnoService;
import com.ipn.mx.celex.application.dto.AlumnoDTO;
import com.ipn.mx.celex.application.mapper.AlumnoMapper;
import com.ipn.mx.celex.domain.entities.Alumno;
import com.ipn.mx.celex.domain.repository.AlumnoRepository;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository repository;

    public AlumnoServiceImpl(AlumnoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoDTO> findAll() {
        return repository.findAll().stream().map(AlumnoMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoDTO findById(Long id) {
        return AlumnoMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional
    public AlumnoDTO save(AlumnoDTO dto) {
        Alumno entity = AlumnoMapper.toEntity(dto);
        entity.setIdAlumno(null);
        Alumno saved = repository.save(entity);
        return AlumnoMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public AlumnoDTO update(Long id, AlumnoDTO dto) {
        Alumno existing = getEntity(id);
        existing.setBoleta(dto.getBoleta());
        existing.setNombre(dto.getNombre());
        existing.setApellidos(dto.getApellidos());
        existing.setCorreo(dto.getCorreo());
        return AlumnoMapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Alumno no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private Alumno getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + id));
    }
}
