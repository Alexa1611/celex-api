package com.ipn.mx.celex.application;

import com.ipn.mx.celex.application.dto.CursoDTO;

import java.util.List;

public interface CursoService {
    List<CursoDTO> findAll();
    CursoDTO findById(Long id);
    CursoDTO save(CursoDTO dto);
    CursoDTO update(Long id, CursoDTO dto);
    void deleteById(Long id);
}
