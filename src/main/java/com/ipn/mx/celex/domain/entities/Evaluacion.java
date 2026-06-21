package com.ipn.mx.celex.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Evaluacion")
public class Evaluacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvaluacion")
    private Long idEvaluacion;

    @Column(name = "calificacionSpeaking", nullable = false, precision = 5, scale = 2)
    private BigDecimal calificacionSpeaking;

    @Column(name = "calificacionWriting", nullable = false, precision = 5, scale = 2)
    private BigDecimal calificacionWriting;

    @Column(name = "promedioFinal", nullable = false, precision = 5, scale = 2)
    private BigDecimal promedioFinal;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idInscripcion", nullable = false, unique = true)
    private Inscripcion inscripcion;
}
