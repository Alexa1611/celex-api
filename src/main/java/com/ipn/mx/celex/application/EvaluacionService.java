package com.ipn.mx.celex.application;

import com.ipn.mx.celex.application.dto.EvaluacionDTO;

import java.util.List;

public interface EvaluacionService {
    List<EvaluacionDTO> findAll();
    EvaluacionDTO findById(Long id);
    EvaluacionDTO save(EvaluacionDTO dto);
    EvaluacionDTO update(Long id, EvaluacionDTO dto);
    void deleteById(Long id);
}
