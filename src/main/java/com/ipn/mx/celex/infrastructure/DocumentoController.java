package com.ipn.mx.celex.infrastructure;

import com.ipn.mx.celex.application.DocumentoService;
import com.ipn.mx.celex.application.dto.DocumentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/documentos")
@Tag(name = "Documentos PDF", description = "Subida, consulta y descarga de archivos PDF")
public class DocumentoController {

    private final DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @GetMapping("/documento")
    @Operation(summary = "Listar documentos PDF")
    public List<DocumentoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/documento/{id}")
    @Operation(summary = "Obtener metadatos de un PDF")
    public DocumentoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/inscripcion/{idInscripcion}")
    @Operation(summary = "Listar PDFs de una inscripcion")
    public List<DocumentoDTO> findByInscripcion(@PathVariable Long idInscripcion) {
        return service.findByInscripcionId(idInscripcion);
    }

    @PostMapping(value = "/documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Subir archivo PDF",
            description = "Recibe un PDF, valida su contenido y lo almacena en el servidor. "
                    + "Opcionalmente puede asociarse a una inscripcion.")
    public DocumentoDTO upload(
            @Parameter(description = "Archivo PDF", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE))
            @RequestParam("archivo") MultipartFile archivo,
            @Parameter(description = "Id de inscripcion asociada (opcional)")
            @RequestParam(value = "idInscripcion", required = false) Long idInscripcion,
            @Parameter(description = "Descripcion del documento (opcional)")
            @RequestParam(value = "descripcion", required = false) String descripcion) {
        return service.upload(archivo, idInscripcion, descripcion);
    }

    @GetMapping("/documento/{id}/descargar")
    @Operation(summary = "Descargar PDF almacenado")
    public ResponseEntity<Resource> descargar(@PathVariable Long id) {
        DocumentoDTO meta = service.findById(id);
        Resource resource = service.loadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + meta.getNombreOriginal() + "\"")
                .body(resource);
    }

    @DeleteMapping("/documento/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar PDF")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
