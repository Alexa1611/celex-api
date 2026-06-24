package com.ipn.mx.celex.application.mapper;

import com.ipn.mx.celex.application.dto.DocumentoDTO;
import com.ipn.mx.celex.domain.entities.Documento;

public final class DocumentoMapper {

    private DocumentoMapper() {
    }

    public static DocumentoDTO toDTO(Documento entity) {
        DocumentoDTO dto = new DocumentoDTO();
        dto.setIdDocumento(entity.getIdDocumento());
        dto.setNombreOriginal(entity.getNombreOriginal());
        dto.setTipoContenido(entity.getTipoContenido());
        dto.setTamanoBytes(entity.getTamanoBytes());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaSubida(entity.getFechaSubida());
        dto.setUrlDescarga("/api/v1/documentos/documento/" + entity.getIdDocumento() + "/descargar");

        if (entity.getInscripcion() != null) {
            dto.setIdInscripcion(entity.getInscripcion().getIdInscripcion());
            if (entity.getInscripcion().getAlumno() != null) {
                var alumno = entity.getInscripcion().getAlumno();
                dto.setAlumnoNombre(alumno.getNombre() + " " + alumno.getApellidos());
            }
            if (entity.getInscripcion().getCurso() != null) {
                var curso = entity.getInscripcion().getCurso();
                dto.setCursoNombre(curso.getIdioma() + " - " + curso.getNivel());
            }
        }
        return dto;
    }
}
