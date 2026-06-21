package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.InscripcionService;
import com.ipn.mx.celex.application.dto.InscripcionDTO;
import com.ipn.mx.celex.application.mapper.InscripcionMapper;
import com.ipn.mx.celex.domain.entities.Alumno;
import com.ipn.mx.celex.domain.entities.Curso;
import com.ipn.mx.celex.domain.entities.Inscripcion;
import com.ipn.mx.celex.domain.repository.AlumnoRepository;
import com.ipn.mx.celex.domain.repository.CursoRepository;
import com.ipn.mx.celex.domain.repository.InscripcionRepository;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository repository;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;

    public InscripcionServiceImpl(
            InscripcionRepository repository,
            AlumnoRepository alumnoRepository,
            CursoRepository cursoRepository) {
        this.repository = repository;
        this.alumnoRepository = alumnoRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionDTO> findAll() {
        return repository.findAll().stream().map(InscripcionMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionDTO findById(Long id) {
        return InscripcionMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional
    public InscripcionDTO save(InscripcionDTO dto) {
        Inscripcion inscripcion = buildEntity(dto, new Inscripcion());
        return InscripcionMapper.toDTO(repository.save(inscripcion));
    }

    @Override
    @Transactional
    public InscripcionDTO update(Long id, InscripcionDTO dto) {
        Inscripcion existing = getEntity(id);
        buildEntity(dto, existing);
        return InscripcionMapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Inscripcion no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    private Inscripcion buildEntity(InscripcionDTO dto, Inscripcion inscripcion) {
        Alumno alumno = alumnoRepository.findById(dto.getIdAlumno())
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + dto.getIdAlumno()));
        Curso curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + dto.getIdCurso()));
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());
        inscripcion.setPeriodo(dto.getPeriodo());
        inscripcion.setAlumno(alumno);
        inscripcion.setCurso(curso);
        return inscripcion;
    }

    private Inscripcion getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion no encontrada con id: " + id));
    }
}
