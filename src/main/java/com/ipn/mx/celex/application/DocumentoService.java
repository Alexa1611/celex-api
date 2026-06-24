package com.ipn.mx.celex.application;

import com.ipn.mx.celex.application.dto.DocumentoDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentoService {

    List<DocumentoDTO> findAll();

    List<DocumentoDTO> findByInscripcionId(Long idInscripcion);

    DocumentoDTO findById(Long id);

    DocumentoDTO upload(MultipartFile archivo, Long idInscripcion, String descripcion);

    Resource loadFile(Long id);

    void deleteById(Long id);
}
