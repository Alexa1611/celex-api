package com.ipn.mx.celex.application.mapper;

import com.ipn.mx.celex.application.dto.EvaluacionDTO;
import com.ipn.mx.celex.domain.entities.Evaluacion;

public final class EvaluacionMapper {

    private EvaluacionMapper() {
    }

    public static EvaluacionDTO toDTO(Evaluacion entity) {
        EvaluacionDTO dto = new EvaluacionDTO();
        dto.setIdEvaluacion(entity.getIdEvaluacion());
        dto.setCalificacionSpeaking(entity.getCalificacionSpeaking());
        dto.setCalificacionWriting(entity.getCalificacionWriting());
        dto.setPromedioFinal(entity.getPromedioFinal());
        if (entity.getInscripcion() != null) {
            dto.setIdInscripcion(entity.getInscripcion().getIdInscripcion());
        }
        return dto;
    }
}
