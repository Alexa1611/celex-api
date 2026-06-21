package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.CursoService;
import com.ipn.mx.celex.application.dto.CursoDTO;
import com.ipn.mx.celex.application.mapper.CursoMapper;
import com.ipn.mx.celex.domain.entities.Curso;
import com.ipn.mx.celex.domain.entities.Profesor;
import com.ipn.mx.celex.domain.repository.CursoRepository;
import com.ipn.mx.celex.domain.repository.ProfesorRepository;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository repository;
    private final ProfesorRepository profesorRepository;

    public CursoServiceImpl(CursoRepository repository, ProfesorRepository profesorRepository) {
        this.repository = repository;
        this.profesorRepository = profesorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoDTO> findAll() {
        return repository.findAll().stream().map(CursoMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoDTO findById(Long id) {
        return CursoMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional
    public CursoDTO save(CursoDTO dto) {
        Curso curso = buildEntity(dto, new Curso());
        return CursoMapper.toDTO(repository.save(curso));
    }

    @Override
    @Transactional
    public CursoDTO update(Long id, CursoDTO dto) {
        Curso existing = getEntity(id);
        buildEntity(dto, existing);
        return CursoMapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private Curso buildEntity(CursoDTO dto, Curso curso) {
        Profesor profesor = profesorRepository.findById(dto.getIdProfesor())
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + dto.getIdProfesor()));
        curso.setIdioma(dto.getIdioma());
        curso.setNivel(dto.getNivel());
        curso.setHorario(dto.getHorario());
        curso.setSalon(dto.getSalon());
        curso.setProfesor(profesor);
        return curso;
    }

    private Curso getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }
}
