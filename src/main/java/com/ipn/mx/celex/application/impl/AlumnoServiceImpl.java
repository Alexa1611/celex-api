package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.AlumnoService;
import com.ipn.mx.celex.application.EmailService;
import com.ipn.mx.celex.application.dto.AlumnoDTO;
import com.ipn.mx.celex.application.mapper.AlumnoMapper;
import com.ipn.mx.celex.domain.entities.Alumno;
import com.ipn.mx.celex.domain.repository.AlumnoRepository;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private static final Logger log = LoggerFactory.getLogger(AlumnoServiceImpl.class);

    private final AlumnoRepository repository;
    private final EmailService emailService;

    public AlumnoServiceImpl(AlumnoRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
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
        enviarCorreoRegistro(saved);
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

    private void enviarCorreoRegistro(Alumno alumno) {
        try {
            emailService.sendAlumnoRegistro(alumno);
        } catch (Exception ex) {
            log.warn("Alumno guardado, pero no se envio correo: {}", ex.getMessage());
        }
    }
}
