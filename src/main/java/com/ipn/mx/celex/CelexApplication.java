package com.ipn.mx.celex;

import com.ipn.mx.celex.application.dto.*;
import com.ipn.mx.celex.application.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class CelexApplication implements CommandLineRunner {

    private final AlumnoService alumnoService;
    private final ProfesorService profesorService;
    private final CursoService cursoService;
    private final InscripcionService inscripcionService;
    private final EvaluacionService evaluacionService;

    public CelexApplication(
            AlumnoService alumnoService,
            ProfesorService profesorService,
            CursoService cursoService,
            InscripcionService inscripcionService,
            EvaluacionService evaluacionService) {
        this.alumnoService = alumnoService;
        this.profesorService = profesorService;
        this.cursoService = cursoService;
        this.inscripcionService = inscripcionService;
        this.evaluacionService = evaluacionService;
    }

    @Override
    public void run(String... args) {
        if (alumnoService.findAll().isEmpty()) {
            seedData();
        }

        System.out.println("Conexion exitosa a YugabyteDB");
        System.out.println("Alumnos registrados: " + alumnoService.findAll().size());
        System.out.println("Profesores registrados: " + profesorService.findAll().size());
        System.out.println("Cursos registrados: " + cursoService.findAll().size());
        System.out.println("Inscripciones registradas: " + inscripcionService.findAll().size());
        System.out.println("Evaluaciones registradas: " + evaluacionService.findAll().size());
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
    }

    private void seedData() {
        ProfesorDTO profesor = profesorService.save(buildProfesor(
                "EMP-001", "Maria", "Lopez", "Ingles"));

        AlumnoDTO alumno = alumnoService.save(buildAlumno(
                2021310001L, "Alexa", "Cruz Mejia", "alexamejia1611@gmail.com"));

        CursoDTO curso = cursoService.save(buildCurso(
                "Ingles", "Basico", "Lunes y Miercoles 10:00-12:00", "A-101", profesor.getIdProfesor()));

        InscripcionDTO inscripcion = inscripcionService.save(buildInscripcion(
                LocalDate.now(), "2026-1", alumno.getIdAlumno(), curso.getIdCurso()));

        EvaluacionDTO evaluacion = new EvaluacionDTO();
        evaluacion.setCalificacionSpeaking(new BigDecimal("85.50"));
        evaluacion.setCalificacionWriting(new BigDecimal("90.00"));
        evaluacion.setIdInscripcion(inscripcion.getIdInscripcion());
        evaluacionService.save(evaluacion);
    }

    private ProfesorDTO buildProfesor(String numEmpleado, String nombre, String apellidos, String idioma) {
        ProfesorDTO dto = new ProfesorDTO();
        dto.setNumEmpleado(numEmpleado);
        dto.setNombre(nombre);
        dto.setApellidos(apellidos);
        dto.setIdiomaEspecialidad(idioma);
        return dto;
    }

    private AlumnoDTO buildAlumno(Long boleta, String nombre, String apellidos, String correo) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setBoleta(boleta);
        dto.setNombre(nombre);
        dto.setApellidos(apellidos);
        dto.setCorreo(correo);
        return dto;
    }

    private CursoDTO buildCurso(String idioma, String nivel, String horario, String salon, Long idProfesor) {
        CursoDTO dto = new CursoDTO();
        dto.setIdioma(idioma);
        dto.setNivel(nivel);
        dto.setHorario(horario);
        dto.setSalon(salon);
        dto.setIdProfesor(idProfesor);
        return dto;
    }

    private InscripcionDTO buildInscripcion(LocalDate fecha, String periodo, Long idAlumno, Long idCurso) {
        InscripcionDTO dto = new InscripcionDTO();
        dto.setFechaInscripcion(fecha);
        dto.setPeriodo(periodo);
        dto.setIdAlumno(idAlumno);
        dto.setIdCurso(idCurso);
        return dto;
    }

    public static void main(String[] args) {
        SpringApplication.run(CelexApplication.class, args);
    }
}
