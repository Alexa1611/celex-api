package com.ipn.mx.celex.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Documento")
public class Documento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDocumento")
    private Long idDocumento;

    @Column(name = "nombreOriginal", length = 255, nullable = false)
    private String nombreOriginal;

    @Column(name = "nombreAlmacenado", length = 255, nullable = false, unique = true)
    private String nombreAlmacenado;

    @Column(name = "tipoContenido", length = 100, nullable = false)
    private String tipoContenido;

    @Column(name = "tamanoBytes", nullable = false)
    private Long tamanoBytes;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInscripcion")
    private Inscripcion inscripcion;

    @Column(name = "fechaSubida", nullable = false)
    private LocalDateTime fechaSubida;
}
