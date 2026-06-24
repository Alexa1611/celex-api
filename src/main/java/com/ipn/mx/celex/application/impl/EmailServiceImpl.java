package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.EmailService;
import com.ipn.mx.celex.domain.entities.Alumno;
import com.ipn.mx.celex.domain.entities.Curso;
import com.ipn.mx.celex.domain.entities.Inscripcion;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final String fromAddress;
    private final String fromName;
    private final boolean enabled;

    public EmailServiceImpl(
            JavaMailSender mailSender,
            @Value("${celex.mail.from:}") String fromAddress,
            @Value("${celex.mail.from-name:CELEX_FALSO}") String fromName,
            @Value("${celex.mail.enabled:false}") boolean enabled) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
        this.fromName = fromName;
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void send(String to, String subject, String text) {
        validateConfiguration();
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setFrom(fromAddress, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);
            mailSender.send(message);
            log.info("Correo enviado a {}", to);
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo enviar el correo: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void sendAlumnoRegistro(Alumno alumno) {
        if (!enabled) {
            log.debug("SMTP deshabilitado; se omite correo de registro de alumno");
            return;
        }

        String subject = "CELEX - Bienvenida al sistema";
        String text = """
                Hola %s %s,

                Tu registro en CELEX fue exitoso.

                Boleta: %s
                Correo registrado: %s

                Ya puedes inscribirte a cursos de idiomas del IPN.

                CELEX - Centro de Lenguas Extranjeras
                Instituto Politecnico Nacional
                """.formatted(
                alumno.getNombre(),
                alumno.getApellidos(),
                alumno.getBoleta(),
                alumno.getCorreo());

        send(alumno.getCorreo(), subject, text);
    }

    @Override
    public void sendInscripcionConfirmacion(Inscripcion inscripcion) {
        if (!enabled) {
            log.debug("SMTP deshabilitado; se omite correo de inscripcion");
            return;
        }

        Alumno alumno = inscripcion.getAlumno();
        Curso curso = inscripcion.getCurso();
        String subject = "CELEX - Confirmacion de inscripcion";
        String text = """
                Hola %s %s,

                Tu inscripcion al curso %s - %s fue registrada correctamente.

                Periodo: %s
                Fecha: %s
                Horario: %s
                Salon: %s

                CELEX - Centro de Lenguas Extranjeras
                Instituto Politecnico Nacional
                """.formatted(
                alumno.getNombre(),
                alumno.getApellidos(),
                curso.getIdioma(),
                curso.getNivel(),
                inscripcion.getPeriodo(),
                inscripcion.getFechaInscripcion(),
                curso.getHorario(),
                curso.getSalon());

        send(alumno.getCorreo(), subject, text);
    }

    private void validateConfiguration() {
        if (!enabled) {
            throw new IllegalStateException(
                    "El servicio SMTP no esta configurado. Define MAIL_ENABLED=true y las variables MAIL_*.");
        }
        if (fromAddress == null || fromAddress.isBlank()) {
            throw new IllegalStateException("MAIL_FROM o MAIL_USERNAME no esta configurado.");
        }
    }
}
