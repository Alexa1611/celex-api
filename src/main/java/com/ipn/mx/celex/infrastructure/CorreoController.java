package com.ipn.mx.celex.infrastructure;

import com.ipn.mx.celex.application.EmailService;
import com.ipn.mx.celex.application.dto.CorreoDTO;
import com.ipn.mx.celex.application.impl.EmailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/correos")
@Tag(name = "Correos", description = "Envio de correos via Brevo API (Render) o Jakarta Mail SMTP (local)")
public class CorreoController {

    private final EmailService emailService;

    public CorreoController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/correo")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Enviar correo electronico",
            description = "Envia correo con Brevo API si BREVO_API_KEY esta definida; "
                    + "en local usa Jakarta Mail y Gmail SMTP.")
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
        String proveedor = emailService instanceof EmailServiceImpl impl && impl.usesBrevo()
                ? "brevo-api"
                : "smtp";
        return Map.of(
                "smtpHabilitado", emailService.isEnabled(),
                "proveedor", emailService.isEnabled() ? proveedor : "ninguno");
    }
}
