package com.ipn.mx.celex.application.mapper;

import com.ipn.mx.celex.application.dto.ProfesorDTO;
import com.ipn.mx.celex.domain.entities.Profesor;

public final class ProfesorMapper {

    private ProfesorMapper() {
    }

    public static ProfesorDTO toDTO(Profesor entity) {
        ProfesorDTO dto = new ProfesorDTO();
        dto.setIdProfesor(entity.getIdProfesor());
        dto.setNumEmpleado(entity.getNumEmpleado());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setIdiomaEspecialidad(entity.getIdiomaEspecialidad());
        return dto;
    }

    public static Profesor toEntity(ProfesorDTO dto) {
        return Profesor.builder()
                .idProfesor(dto.getIdProfesor())
                .numEmpleado(dto.getNumEmpleado())
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .idiomaEspecialidad(dto.getIdiomaEspecialidad())
                .build();
    }
}
