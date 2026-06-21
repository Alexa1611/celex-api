package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.EvaluacionService;
import com.ipn.mx.celex.application.dto.EvaluacionDTO;
import com.ipn.mx.celex.application.mapper.EvaluacionMapper;
import com.ipn.mx.celex.domain.entities.Evaluacion;
import com.ipn.mx.celex.domain.entities.Inscripcion;
import com.ipn.mx.celex.domain.repository.EvaluacionRepository;
import com.ipn.mx.celex.domain.repository.InscripcionRepository;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository repository;
    private final InscripcionRepository inscripcionRepository;

    public EvaluacionServiceImpl(EvaluacionRepository repository, InscripcionRepository inscripcionRepository) {
        this.repository = repository;
        this.inscripcionRepository = inscripcionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluacionDTO> findAll() {
        return repository.findAll().stream().map(EvaluacionMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluacionDTO findById(Long id) {
        return EvaluacionMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional
    public EvaluacionDTO save(EvaluacionDTO dto) {
        Evaluacion evaluacion = buildEntity(dto, new Evaluacion());
        return EvaluacionMapper.toDTO(repository.save(evaluacion));
    }

    @Override
    @Transactional
    public EvaluacionDTO update(Long id, EvaluacionDTO dto) {
        Evaluacion existing = getEntity(id);
        buildEntity(dto, existing);
        return EvaluacionMapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Evaluacion no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    private Evaluacion buildEntity(EvaluacionDTO dto, Evaluacion evaluacion) {
        Inscripcion inscripcion = inscripcionRepository.findById(dto.getIdInscripcion())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inscripcion no encontrada con id: " + dto.getIdInscripcion()));
        evaluacion.setCalificacionSpeaking(dto.getCalificacionSpeaking());
        evaluacion.setCalificacionWriting(dto.getCalificacionWriting());
        evaluacion.setPromedioFinal(calcularPromedio(dto));
        evaluacion.setInscripcion(inscripcion);
        return evaluacion;
    }

    private BigDecimal calcularPromedio(EvaluacionDTO dto) {
        if (dto.getPromedioFinal() != null) {
            return dto.getPromedioFinal();
        }
        return dto.getCalificacionSpeaking()
                .add(dto.getCalificacionWriting())
                .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
    }

    private Evaluacion getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluacion no encontrada con id: " + id));
    }
}
