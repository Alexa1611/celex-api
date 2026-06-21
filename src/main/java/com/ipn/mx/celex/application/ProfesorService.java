package com.ipn.mx.celex.application;

import com.ipn.mx.celex.application.dto.ProfesorDTO;

import java.util.List;

public interface ProfesorService {
    List<ProfesorDTO> findAll();
    ProfesorDTO findById(Long id);
    ProfesorDTO save(ProfesorDTO dto);
    ProfesorDTO update(Long id, ProfesorDTO dto);
    void deleteById(Long id);
}
