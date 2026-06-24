package com.ipn.mx.celex.infrastructure;

import com.ipn.mx.celex.application.AlumnoService;
import com.ipn.mx.celex.application.dto.AlumnoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/alumnos")
@Tag(name = "Alumnos", description = "CRUD de alumnos")
public class AlumnoController {

    private final AlumnoService service;

    public AlumnoController(AlumnoService service) {
        this.service = service;
    }

    @GetMapping("/alumno")
    @Operation(summary = "Listar alumnos")
    public List<AlumnoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/alumno/{id}")
    @Operation(summary = "Obtener alumno por id")
    public AlumnoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/alumno")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar alumno",
            description = "Registra un alumno y envia correo de bienvenida si SMTP esta habilitado.")
    public AlumnoDTO save(@Valid @RequestBody AlumnoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/alumno/{id}")
    @Operation(summary = "Actualizar alumno")
    public AlumnoDTO update(@PathVariable Long id, @Valid @RequestBody AlumnoDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/alumno/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar alumno")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
