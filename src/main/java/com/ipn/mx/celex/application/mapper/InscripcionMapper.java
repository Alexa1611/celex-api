package com.ipn.mx.celex.application.mapper;

import com.ipn.mx.celex.application.dto.InscripcionDTO;
import com.ipn.mx.celex.domain.entities.Inscripcion;

public final class InscripcionMapper {

    private InscripcionMapper() {
    }

    public static InscripcionDTO toDTO(Inscripcion entity) {
        InscripcionDTO dto = new InscripcionDTO();
        dto.setIdInscripcion(entity.getIdInscripcion());
        dto.setFechaInscripcion(entity.getFechaInscripcion());
        dto.setPeriodo(entity.getPeriodo());
        if (entity.getAlumno() != null) {
            dto.setIdAlumno(entity.getAlumno().getIdAlumno());
            dto.setAlumnoNombre(entity.getAlumno().getNombre() + " " + entity.getAlumno().getApellidos());
        }
        if (entity.getCurso() != null) {
            dto.setIdCurso(entity.getCurso().getIdCurso());
            dto.setCursoNombre(entity.getCurso().getIdioma() + " - " + entity.getCurso().getNivel());
        }
        return dto;
    }
}
