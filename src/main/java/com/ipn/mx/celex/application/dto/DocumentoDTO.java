package com.ipn.mx.celex.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Metadatos de un documento PDF almacenado")
public class DocumentoDTO {

    private Long idDocumento;
    private String nombreOriginal;
    private String tipoContenido;
    private Long tamanoBytes;
    private String descripcion;
    private Long idInscripcion;
    private String alumnoNombre;
    private String cursoNombre;
    private LocalDateTime fechaSubida;

    @Schema(description = "URL relativa para descargar el PDF")
    private String urlDescarga;
}
