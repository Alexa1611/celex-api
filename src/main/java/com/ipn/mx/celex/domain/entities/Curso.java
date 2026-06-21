package com.ipn.mx.celex.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Curso")
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCurso")
    private Long idCurso;

    @Column(name = "idioma", length = 50, nullable = false)
    private String idioma;

    @Column(name = "nivel", length = 20, nullable = false)
    private String nivel;

    @Column(name = "horario", length = 50, nullable = false)
    private String horario;

    @Column(name = "salon", length = 20, nullable = false)
    private String salon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idProfesor", nullable = false)
    private Profesor profesor;
}
