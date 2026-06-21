package com.ipn.mx.celex.infrastructure;

import com.ipn.mx.celex.application.InscripcionService;
import com.ipn.mx.celex.application.dto.InscripcionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/inscripciones")
@Tag(name = "Inscripciones", description = "CRUD de inscripciones")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @GetMapping("/inscripcion")
    @Operation(summary = "Listar inscripciones")
    public List<InscripcionDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/inscripcion/{id}")
    @Operation(summary = "Obtener inscripcion por id")
    public InscripcionDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/inscripcion")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar inscripcion")
    public InscripcionDTO save(@Valid @RequestBody InscripcionDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/inscripcion/{id}")
    @Operation(summary = "Actualizar inscripcion")
    public InscripcionDTO update(@PathVariable Long id, @Valid @RequestBody InscripcionDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/inscripcion/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar inscripcion")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
