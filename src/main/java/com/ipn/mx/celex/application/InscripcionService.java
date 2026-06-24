package com.ipn.mx.celex.application;

import com.ipn.mx.celex.application.dto.InscripcionDTO;

import java.util.List;

public interface InscripcionService {
    List<InscripcionDTO> findAll();
    InscripcionDTO findById(Long id);
    InscripcionDTO save(InscripcionDTO dto);
    InscripcionDTO update(Long id, InscripcionDTO dto);
    void deleteById(Long id);
    byte[] generateConstanciaPdf(Long id);
}
