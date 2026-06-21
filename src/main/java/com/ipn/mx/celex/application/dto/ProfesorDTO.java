package com.ipn.mx.celex.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfesorDTO {
    private Long idProfesor;

    @NotBlank(message = "El numero de empleado es obligatorio")
    private String numEmpleado;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "El idioma de especialidad es obligatorio")
    private String idiomaEspecialidad;
}
