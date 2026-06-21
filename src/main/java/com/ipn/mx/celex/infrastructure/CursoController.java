package com.ipn.mx.celex.infrastructure;

import com.ipn.mx.celex.application.CursoService;
import com.ipn.mx.celex.application.dto.CursoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/cursos")
@Tag(name = "Cursos", description = "CRUD de cursos de idiomas")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping("/curso")
    @Operation(summary = "Listar cursos")
    public List<CursoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/curso/{id}")
    @Operation(summary = "Obtener curso por id")
    public CursoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/curso")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar curso")
    public CursoDTO save(@Valid @RequestBody CursoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/curso/{id}")
    @Operation(summary = "Actualizar curso")
    public CursoDTO update(@PathVariable Long id, @Valid @RequestBody CursoDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/curso/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar curso")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
