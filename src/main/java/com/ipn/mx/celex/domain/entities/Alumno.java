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
@Table(name = "Alumno")
public class Alumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlumno")
    private Long idAlumno;

    @Column(name = "boleta", nullable = false, unique = true)
    private Long boleta;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @Column(name = "correo", length = 100, nullable = false, unique = true)
    private String correo;
}
