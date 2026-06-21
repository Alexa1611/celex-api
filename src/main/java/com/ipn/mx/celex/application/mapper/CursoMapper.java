package com.ipn.mx.celex.application.mapper;

import com.ipn.mx.celex.application.dto.CursoDTO;
import com.ipn.mx.celex.domain.entities.Curso;

public final class CursoMapper {

    private CursoMapper() {
    }

    public static CursoDTO toDTO(Curso entity) {
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(entity.getIdCurso());
        dto.setIdioma(entity.getIdioma());
        dto.setNivel(entity.getNivel());
        dto.setHorario(entity.getHorario());
        dto.setSalon(entity.getSalon());
        if (entity.getProfesor() != null) {
            dto.setIdProfesor(entity.getProfesor().getIdProfesor());
            dto.setProfesorNombre(entity.getProfesor().getNombre() + " " + entity.getProfesor().getApellidos());
        }
        return dto;
    }
}
