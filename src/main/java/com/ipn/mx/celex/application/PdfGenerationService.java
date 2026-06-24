package com.ipn.mx.celex.application;

import com.ipn.mx.celex.domain.entities.Inscripcion;

public interface PdfGenerationService {
    byte[] generateConstanciaInscripcion(Inscripcion inscripcion);
}
