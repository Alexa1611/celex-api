package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.DocumentoService;
import com.ipn.mx.celex.application.FileStorageService;
import com.ipn.mx.celex.application.dto.DocumentoDTO;
import com.ipn.mx.celex.application.mapper.DocumentoMapper;
import com.ipn.mx.celex.domain.entities.Documento;
import com.ipn.mx.celex.domain.entities.Inscripcion;
import com.ipn.mx.celex.domain.repository.DocumentoRepository;
import com.ipn.mx.celex.domain.repository.InscripcionRepository;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository repository;
    private final InscripcionRepository inscripcionRepository;
    private final FileStorageService fileStorageService;

    public DocumentoServiceImpl(
            DocumentoRepository repository,
            InscripcionRepository inscripcionRepository,
            FileStorageService fileStorageService) {
        this.repository = repository;
        this.inscripcionRepository = inscripcionRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> findAll() {
        return repository.findAll().stream().map(DocumentoMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> findByInscripcionId(Long idInscripcion) {
        return repository.findByInscripcion_IdInscripcionOrderByFechaSubidaDesc(idInscripcion)
                .stream()
                .map(DocumentoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoDTO findById(Long id) {
        return DocumentoMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional
    public DocumentoDTO upload(MultipartFile archivo, Long idInscripcion, String descripcion) {
        Inscripcion inscripcion = null;
        if (idInscripcion != null) {
            inscripcion = inscripcionRepository.findById(idInscripcion)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Inscripcion no encontrada con id: " + idInscripcion));
        }

        String storedName = fileStorageService.storePdf(archivo);
        String contentType = archivo.getContentType() != null ? archivo.getContentType() : "application/pdf";

        Documento documento = Documento.builder()
                .nombreOriginal(archivo.getOriginalFilename())
                .nombreAlmacenado(storedName)
                .tipoContenido(contentType)
                .tamanoBytes(archivo.getSize())
                .descripcion(descripcion)
                .inscripcion(inscripcion)
                .fechaSubida(LocalDateTime.now())
                .build();

        return DocumentoMapper.toDTO(repository.save(documento));
    }

    @Override
    @Transactional(readOnly = true)
    public Resource loadFile(Long id) {
        Documento documento = getEntity(id);
        return fileStorageService.loadAsResource(documento.getNombreAlmacenado());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Documento documento = getEntity(id);
        fileStorageService.delete(documento.getNombreAlmacenado());
        repository.delete(documento);
    }

    private Documento getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento no encontrado con id: " + id));
    }
}
