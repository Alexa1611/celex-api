package com.ipn.mx.celex.application;

import com.ipn.mx.celex.domain.entities.Alumno;
import com.ipn.mx.celex.domain.entities.Inscripcion;

public interface EmailService {

    void send(String to, String subject, String text);

    void sendAlumnoRegistro(Alumno alumno);

    void sendInscripcionConfirmacion(Inscripcion inscripcion);

    boolean isEnabled();
}
