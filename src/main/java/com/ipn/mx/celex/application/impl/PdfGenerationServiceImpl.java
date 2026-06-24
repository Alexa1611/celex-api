package com.ipn.mx.celex.application.impl;

import com.ipn.mx.celex.application.PdfGenerationService;
import com.ipn.mx.celex.domain.entities.Alumno;
import com.ipn.mx.celex.domain.entities.Curso;
import com.ipn.mx.celex.domain.entities.Inscripcion;
import com.ipn.mx.celex.domain.entities.Profesor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {

    private static final Font TITLE = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    private static final Font HEADER = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    private static final Font BODY = FontFactory.getFont(FontFactory.HELVETICA, 11);
    private static final Font FOOTER = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9);

    @Override
    public byte[] generateConstanciaInscripcion(Inscripcion inscripcion) {
        Alumno alumno = inscripcion.getAlumno();
        Curso curso = inscripcion.getCurso();
        Profesor profesor = curso.getProfesor();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);
            PdfWriter.getInstance(document, output);
            document.open();

            Paragraph title = new Paragraph("CELEX FALSO", TITLE);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("Constancia de Inscripcion", HEADER);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20f);
            document.add(subtitle);

            document.add(new Paragraph("Instituto Politecnico Nacional - ESCOM", BODY));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(
                    "Por medio de la presente se hace constar que el siguiente alumno "
                            + "esta inscrito en un curso de idiomas del CELEX:",
                    BODY));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Alumno: " + alumno.getNombre() + " " + alumno.getApellidos(), BODY));
            document.add(new Paragraph("Boleta: " + alumno.getBoleta(), BODY));
            document.add(new Paragraph("Correo: " + alumno.getCorreo(), BODY));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Curso: " + curso.getIdioma() + " - " + curso.getNivel(), BODY));
            document.add(new Paragraph("Horario: " + curso.getHorario(), BODY));
            document.add(new Paragraph("Salon: " + curso.getSalon(), BODY));
            document.add(new Paragraph(
                    "Profesor: " + profesor.getNombre() + " " + profesor.getApellidos(), BODY));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Periodo: " + inscripcion.getPeriodo(), BODY));
            document.add(new Paragraph("Fecha de inscripcion: " + inscripcion.getFechaInscripcion(), BODY));
            document.add(new Paragraph("Folio: " + inscripcion.getIdInscripcion(), BODY));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(
                    "Documento generado automaticamente por el sistema CELEX FALSO.",
                    FOOTER));

            document.close();
            return output.toByteArray();
        } catch (DocumentException ex) {
            throw new IllegalStateException("No se pudo generar el PDF: " + ex.getMessage(), ex);
        }
    }
}
