package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.EmailService;
import com.ipn.mx.celex.application.InscripcionService;
import com.ipn.mx.celex.application.PdfGenerationService;
import com.ipn.mx.celex.application.dto.InscripcionDTO;
import com.ipn.mx.celex.application.mapper.InscripcionMapper;
import com.ipn.mx.celex.domain.entities.Alumno;
import com.ipn.mx.celex.domain.entities.Curso;
import com.ipn.mx.celex.domain.entities.Inscripcion;
import com.ipn.mx.celex.domain.repository.AlumnoRepository;
import com.ipn.mx.celex.domain.repository.CursoRepository;
import com.ipn.mx.celex.domain.repository.InscripcionRepository;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    private static final Logger log = LoggerFactory.getLogger(InscripcionServiceImpl.class);

    private final InscripcionRepository repository;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final EmailService emailService;
    private final PdfGenerationService pdfGenerationService;

    public InscripcionServiceImpl(
            InscripcionRepository repository,
            AlumnoRepository alumnoRepository,
            CursoRepository cursoRepository,
            EmailService emailService,
            PdfGenerationService pdfGenerationService) {
        this.repository = repository;
        this.alumnoRepository = alumnoRepository;
        this.cursoRepository = cursoRepository;
        this.emailService = emailService;
        this.pdfGenerationService = pdfGenerationService;
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
        Inscripcion saved = repository.save(inscripcion);
        enviarCorreoConfirmacion(saved);
        return InscripcionMapper.toDTO(saved);
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

    @Override
    @Transactional(readOnly = true)
    public byte[] generateConstanciaPdf(Long id) {
        return pdfGenerationService.generateConstanciaInscripcion(getEntity(id));
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

    private void enviarCorreoConfirmacion(Inscripcion inscripcion) {
        try {
            emailService.sendInscripcionConfirmacion(inscripcion);
        } catch (Exception ex) {
            log.warn("Inscripcion guardada, pero no se envio correo: {}", ex.getMessage());
        }
    }
}
