package com.ipn.mx.celex.infrastructure;

import com.ipn.mx.celex.application.EmailService;
import com.ipn.mx.celex.application.dto.CorreoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/correos")
@Tag(name = "Correos", description = "Envio de correos electronicos via SMTP (Gmail)")
public class CorreoController {

    private final EmailService emailService;

    public CorreoController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/correo")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Enviar correo electronico",
            description = "Envia un correo usando Jakarta Mail y SMTP de Gmail. "
                    + "Requiere MAIL_ENABLED=true y credenciales en variables de entorno.")
    public Map<String, String> enviar(@Valid @RequestBody CorreoDTO dto) {
        emailService.send(dto.getDestinatario(), dto.getAsunto(), dto.getMensaje());
        return Map.of(
                "mensaje", "Correo enviado correctamente",
                "destinatario", dto.getDestinatario());
    }

    @GetMapping("/estado")
    @Operation(summary = "Estado del servicio SMTP",
            description = "Indica si el envio de correos esta habilitado via variables de entorno.")
    public Map<String, Object> estado() {
        return Map.of("smtpHabilitado", emailService.isEnabled());
    }
}
