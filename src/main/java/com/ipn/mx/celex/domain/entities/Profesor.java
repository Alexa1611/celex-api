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
@Table(name = "Profesor")
public class Profesor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfesor")
    private Long idProfesor;

    @Column(name = "numEmpleado", length = 20, nullable = false, unique = true)
    private String numEmpleado;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @Column(name = "idiomaEspecialidad", length = 50, nullable = false)
    private String idiomaEspecialidad;
}
