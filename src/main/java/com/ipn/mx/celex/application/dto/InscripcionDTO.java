package com.ipn.mx.celex.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InscripcionDTO {
    private Long idInscripcion;

    @NotNull(message = "La fecha de inscripcion es obligatoria")
    private LocalDate fechaInscripcion;

    @NotBlank(message = "El periodo es obligatorio")
    private String periodo;

    @NotNull(message = "El id del alumno es obligatorio")
    private Long idAlumno;

    @NotNull(message = "El id del curso es obligatorio")
    private Long idCurso;

    private String alumnoNombre;
    private String cursoNombre;
}
