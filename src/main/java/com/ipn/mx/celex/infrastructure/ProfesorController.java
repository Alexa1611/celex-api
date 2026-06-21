package com.ipn.mx.celex.infrastructure;

import com.ipn.mx.celex.application.ProfesorService;
import com.ipn.mx.celex.application.dto.ProfesorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/profesores")
@Tag(name = "Profesores", description = "CRUD de profesores")
public class ProfesorController {

    private final ProfesorService service;

    public ProfesorController(ProfesorService service) {
        this.service = service;
    }

    @GetMapping("/profesor")
    @Operation(summary = "Listar profesores")
    public List<ProfesorDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/profesor/{id}")
    @Operation(summary = "Obtener profesor por id")
    public ProfesorDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/profesor")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar profesor")
    public ProfesorDTO save(@Valid @RequestBody ProfesorDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/profesor/{id}")
    @Operation(summary = "Actualizar profesor")
    public ProfesorDTO update(@PathVariable Long id, @Valid @RequestBody ProfesorDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/profesor/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar profesor")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
