package com.ipn.mx.celex.infrastructure;

import com.ipn.mx.celex.application.EvaluacionService;
import com.ipn.mx.celex.application.dto.EvaluacionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/evaluaciones")
@Tag(name = "Evaluaciones", description = "CRUD de evaluaciones")
public class EvaluacionController {

    private final EvaluacionService service;

    public EvaluacionController(EvaluacionService service) {
        this.service = service;
    }

    @GetMapping("/evaluacion")
    @Operation(summary = "Listar evaluaciones")
    public List<EvaluacionDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/evaluacion/{id}")
    @Operation(summary = "Obtener evaluacion por id")
    public EvaluacionDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/evaluacion")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar evaluacion")
    public EvaluacionDTO save(@Valid @RequestBody EvaluacionDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/evaluacion/{id}")
    @Operation(summary = "Actualizar evaluacion")
    public EvaluacionDTO update(@PathVariable Long id, @Valid @RequestBody EvaluacionDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/evaluacion/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar evaluacion")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
