package com.ipn.mx.celex.application.mapper;

import com.ipn.mx.celex.application.dto.AlumnoDTO;
import com.ipn.mx.celex.domain.entities.Alumno;

public final class AlumnoMapper {

    private AlumnoMapper() {
    }

    public static AlumnoDTO toDTO(Alumno entity) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setIdAlumno(entity.getIdAlumno());
        dto.setBoleta(entity.getBoleta());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setCorreo(entity.getCorreo());
        return dto;
    }

    public static Alumno toEntity(AlumnoDTO dto) {
        return Alumno.builder()
                .idAlumno(dto.getIdAlumno())
                .boleta(dto.getBoleta())
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .correo(dto.getCorreo())
                .build();
    }
}
