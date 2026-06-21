package com.ipn.mx.celex.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CursoDTO {
    private Long idCurso;

    @NotBlank(message = "El idioma es obligatorio")
    private String idioma;

    @NotBlank(message = "El nivel es obligatorio")
    private String nivel;

    @NotBlank(message = "El horario es obligatorio")
    private String horario;

    @NotBlank(message = "El salon es obligatorio")
    private String salon;

    @NotNull(message = "El id del profesor es obligatorio")
    private Long idProfesor;

    private String profesorNombre;
}
