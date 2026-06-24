-- =============================================================================
-- CELEX - Sistema de Gestion de Cursos de Idiomas (IPN)
-- Script de creacion de base de datos
-- Motor: YugabyteDB / PostgreSQL
-- =============================================================================

-- Opcional: eliminar tablas existentes (orden inverso por dependencias)
-- DROP TABLE IF EXISTS "Evaluacion" CASCADE;
-- DROP TABLE IF EXISTS "Inscripcion" CASCADE;
-- DROP TABLE IF EXISTS "Curso" CASCADE;
-- DROP TABLE IF EXISTS "Profesor" CASCADE;
-- DROP TABLE IF EXISTS "Alumno" CASCADE;

-- -----------------------------------------------------------------------------
-- Tabla: Alumno
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS "Alumno" (
    "idAlumno"   BIGSERIAL PRIMARY KEY,
    "boleta"     BIGINT       NOT NULL UNIQUE,
    "nombre"     VARCHAR(100) NOT NULL,
    "apellidos"  VARCHAR(100) NOT NULL,
    "correo"     VARCHAR(100) NOT NULL UNIQUE
);

-- -----------------------------------------------------------------------------
-- Tabla: Profesor
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS "Profesor" (
    "idProfesor"         BIGSERIAL PRIMARY KEY,
    "numEmpleado"        VARCHAR(20)  NOT NULL UNIQUE,
    "nombre"             VARCHAR(100) NOT NULL,
    "apellidos"          VARCHAR(100) NOT NULL,
    "idiomaEspecialidad" VARCHAR(50)  NOT NULL
);

-- -----------------------------------------------------------------------------
-- Tabla: Curso
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS "Curso" (
    "idCurso"    BIGSERIAL PRIMARY KEY,
    "idioma"     VARCHAR(50)  NOT NULL,
    "nivel"      VARCHAR(20)  NOT NULL,
    "horario"    VARCHAR(50)  NOT NULL,
    "salon"      VARCHAR(20)  NOT NULL,
    "idProfesor" BIGINT       NOT NULL,
    CONSTRAINT "fk_curso_profesor"
        FOREIGN KEY ("idProfesor") REFERENCES "Profesor" ("idProfesor")
);

CREATE INDEX IF NOT EXISTS "idx_curso_profesor" ON "Curso" ("idProfesor");

-- -----------------------------------------------------------------------------
-- Tabla: Inscripcion
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS "Inscripcion" (
    "idInscripcion"      BIGSERIAL PRIMARY KEY,
    "fechaInscripcion"   DATE         NOT NULL,
    "periodo"            VARCHAR(20)  NOT NULL,
    "idAlumno"           BIGINT       NOT NULL,
    "idCurso"            BIGINT       NOT NULL,
    CONSTRAINT "fk_inscripcion_alumno"
        FOREIGN KEY ("idAlumno") REFERENCES "Alumno" ("idAlumno"),
    CONSTRAINT "fk_inscripcion_curso"
        FOREIGN KEY ("idCurso") REFERENCES "Curso" ("idCurso")
);

CREATE INDEX IF NOT EXISTS "idx_inscripcion_alumno" ON "Inscripcion" ("idAlumno");
CREATE INDEX IF NOT EXISTS "idx_inscripcion_curso" ON "Inscripcion" ("idCurso");

-- -----------------------------------------------------------------------------
-- Tabla: Evaluacion
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS "Evaluacion" (
    "idEvaluacion"         BIGSERIAL PRIMARY KEY,
    "calificacionSpeaking" NUMERIC(5, 2) NOT NULL,
    "calificacionWriting"  NUMERIC(5, 2) NOT NULL,
    "promedioFinal"        NUMERIC(5, 2) NOT NULL,
    "idInscripcion"        BIGINT        NOT NULL UNIQUE,
    CONSTRAINT "fk_evaluacion_inscripcion"
        FOREIGN KEY ("idInscripcion") REFERENCES "Inscripcion" ("idInscripcion")
);

CREATE INDEX IF NOT EXISTS "idx_evaluacion_inscripcion" ON "Evaluacion" ("idInscripcion");

-- -----------------------------------------------------------------------------
-- Datos de ejemplo (opcional)
-- -----------------------------------------------------------------------------
-- INSERT INTO "Profesor" ("numEmpleado", "nombre", "apellidos", "idiomaEspecialidad")
-- VALUES ('EMP-001', 'Maria', 'Lopez', 'Ingles');
--
-- INSERT INTO "Alumno" ("boleta", "nombre", "apellidos", "correo")
-- VALUES (2021310001, 'Alexa', 'Cruz Mejia', 'alumno@ejemplo.com');
