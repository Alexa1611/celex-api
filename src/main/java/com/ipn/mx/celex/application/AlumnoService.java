package com.ipn.mx.celex.application;

import com.ipn.mx.celex.application.dto.AlumnoDTO;

import java.util.List;

public interface AlumnoService {
    List<AlumnoDTO> findAll();
    AlumnoDTO findById(Long id);
    AlumnoDTO save(AlumnoDTO dto);
    AlumnoDTO update(Long id, AlumnoDTO dto);
    void deleteById(Long id);
}
