package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.FileStorageService;
import com.ipn.mx.celex.infrastructure.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadDir;

    public FileStorageServiceImpl(@Value("${celex.upload.dir:uploads}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    void init() throws IOException {
        Files.createDirectories(uploadDir);
    }

    @Override
    public String storePdf(MultipartFile file) {
        validatePdf(file);
        String storedName = UUID.randomUUID() + ".pdf";
        Path target = uploadDir.resolve(storedName).normalize();
        if (!target.startsWith(uploadDir)) {
            throw new IllegalArgumentException("Nombre de archivo invalido");
        }
        try {
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo guardar el PDF: " + ex.getMessage(), ex);
        }
        return storedName;
    }

    @Override
    public Resource loadAsResource(String storedFileName) {
        try {
            Path file = resolveStoredPath(storedFileName);
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResourceNotFoundException("Archivo no encontrado: " + storedFileName);
            }
            return resource;
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("Archivo no encontrado: " + storedFileName);
        }
    }

    @Override
    public void delete(String storedFileName) {
        try {
            Files.deleteIfExists(resolveStoredPath(storedFileName));
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo eliminar el PDF: " + ex.getMessage(), ex);
        }
    }

    private Path resolveStoredPath(String storedFileName) {
        Path file = uploadDir.resolve(storedFileName).normalize();
        if (!file.startsWith(uploadDir)) {
            throw new IllegalArgumentException("Ruta de archivo invalida");
        }
        return file;
    }

    private void validatePdf(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo PDF es obligatorio");
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Solo se permiten archivos con extension .pdf");
        }

        byte[] header;
        try {
            header = file.getBytes();
        } catch (IOException ex) {
            throw new IllegalArgumentException("No se pudo leer el archivo PDF");
        }

        if (header.length < 4) {
            throw new IllegalArgumentException("El archivo PDF esta vacio o es invalido");
        }

        String signature = new String(header, 0, 4, StandardCharsets.US_ASCII);
        if (!signature.startsWith("%PDF")) {
            throw new IllegalArgumentException("El contenido no corresponde a un PDF valido");
        }
    }
}
