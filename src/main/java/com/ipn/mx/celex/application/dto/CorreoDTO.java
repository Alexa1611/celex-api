package com.ipn.mx.celex.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos para enviar un correo electronico")
public class CorreoDTO {

    @NotBlank(message = "El destinatario es obligatorio")
    @Email(message = "El destinatario debe ser un correo valido")
    @Schema(example = "alumno@correo.com")
    private String destinatario;

    @NotBlank(message = "El asunto es obligatorio")
    @Schema(example = "Bienvenido a CELEX")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    @Schema(example = "Tu registro en CELEX fue exitoso.")
    private String mensaje;
}
