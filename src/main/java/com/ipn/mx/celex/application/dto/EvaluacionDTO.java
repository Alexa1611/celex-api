package com.ipn.mx.celex.application.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EvaluacionDTO {
    private Long idEvaluacion;

    @NotNull(message = "La calificacion de speaking es obligatoria")
    @DecimalMin(value = "0.0", message = "La calificacion minima es 0")
    @DecimalMax(value = "100.0", message = "La calificacion maxima es 100")
    private BigDecimal calificacionSpeaking;

    @NotNull(message = "La calificacion de writing es obligatoria")
    @DecimalMin(value = "0.0", message = "La calificacion minima es 0")
    @DecimalMax(value = "100.0", message = "La calificacion maxima es 100")
    private BigDecimal calificacionWriting;

    private BigDecimal promedioFinal;

    @NotNull(message = "El id de la inscripcion es obligatorio")
    private Long idInscripcion;
}
