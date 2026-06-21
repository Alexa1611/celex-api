package com.ipn.mx.celex.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Inscripcion")
public class Inscripcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInscripcion")
    private Long idInscripcion;

    @Column(name = "fechaInscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "periodo", length = 20, nullable = false)
    private String periodo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idAlumno", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso curso;
}
