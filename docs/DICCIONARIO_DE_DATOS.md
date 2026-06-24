# Diccionario de Datos — Proyecto CELEX

**Sistema:** Gestión de Cursos de Idiomas (Centro de Lenguas Extranjeras — IPN)  
**Motor de base de datos:** YugabyteDB (compatible PostgreSQL)  
**Backend:** Spring Boot 3.4 · **Frontend:** Angular 19  

---

## 1. Entidad: Alumno

**Descripción:** Persona inscrita en el sistema CELEX que puede tomar cursos de idiomas.

| # | Atributo   | Tipo de dato   | Longitud | Nulo | PK | UK | FK | Descripción                                      |
|---|------------|----------------|----------|------|----|----|----|--------------------------------------------------|
| 1 | idAlumno   | BIGINT         | —        | No   | Sí | —  | —  | Identificador único del alumno (autoincremental) |
| 2 | boleta     | BIGINT         | —        | No   | —  | Sí | —  | Número de boleta del IPN                         |
| 3 | nombre     | VARCHAR        | 100      | No   | —  | —  | —  | Nombre(s) del alumno                             |
| 4 | apellidos  | VARCHAR        | 100      | No   | —  | —  | —  | Apellidos del alumno                             |
| 5 | correo     | VARCHAR        | 100      | No   | —  | Sí | —  | Correo electrónico (contacto y notificaciones)   |

---

## 2. Entidad: Profesor

**Descripción:** Docente que imparte cursos de idiomas en CELEX.

| # | Atributo           | Tipo de dato | Longitud | Nulo | PK | UK | FK | Descripción                                |
|---|--------------------|--------------|----------|------|----|----|----|--------------------------------------------|
| 1 | idProfesor         | BIGINT       | —        | No   | Sí | —  | —  | Identificador único del profesor           |
| 2 | numEmpleado        | VARCHAR      | 20       | No   | —  | Sí | —  | Número de empleado institucional           |
| 3 | nombre             | VARCHAR      | 100      | No   | —  | —  | —  | Nombre(s) del profesor                     |
| 4 | apellidos          | VARCHAR      | 100      | No   | —  | —  | —  | Apellidos del profesor                     |
| 5 | idiomaEspecialidad | VARCHAR      | 50       | No   | —  | —  | —  | Idioma principal que enseña (ej. Inglés)   |

---

## 3. Entidad: Curso

**Descripción:** Oferta académica de un idioma en un nivel, horario y salón específicos.

| # | Atributo   | Tipo de dato | Longitud | Nulo | PK | UK | FK | Descripción                                      |
|---|------------|--------------|----------|------|----|----|----|--------------------------------------------------|
| 1 | idCurso    | BIGINT       | —        | No   | Sí | —  | —  | Identificador único del curso                    |
| 2 | idioma     | VARCHAR      | 50       | No   | —  | —  | —  | Idioma del curso (Inglés, Francés, etc.)       |
| 3 | nivel      | VARCHAR      | 20       | No   | —  | —  | —  | Nivel académico (Básico, Intermedio, etc.)     |
| 4 | horario    | VARCHAR      | 50       | No   | —  | —  | —  | Días y horas de clase                            |
| 5 | salon      | VARCHAR      | 20       | No   | —  | —  | —  | Salón o aula asignada                            |
| 6 | idProfesor | BIGINT       | —        | No   | —  | —  | Sí | Profesor asignado → **Profesor.idProfesor**      |

---

## 4. Entidad: Inscripcion

**Descripción:** Registro de un alumno inscrito a un curso en un periodo escolar.

| # | Atributo         | Tipo de dato | Longitud | Nulo | PK | UK | FK | Descripción                                      |
|---|------------------|--------------|----------|------|----|----|----|--------------------------------------------------|
| 1 | idInscripcion    | BIGINT       | —        | No   | Sí | —  | —  | Identificador único de la inscripción            |
| 2 | fechaInscripcion | DATE         | —        | No   | —  | —  | —  | Fecha en que se realizó la inscripción           |
| 3 | periodo          | VARCHAR      | 20       | No   | —  | —  | —  | Periodo escolar (ej. 2026-1)                     |
| 4 | idAlumno         | BIGINT       | —        | No   | —  | —  | Sí | Alumno inscrito → **Alumno.idAlumno**            |
| 5 | idCurso          | BIGINT       | —        | No   | —  | —  | Sí | Curso seleccionado → **Curso.idCurso**           |

---

## 5. Entidad: Evaluacion

**Descripción:** Calificaciones de desempeño lingüístico de una inscripción (speaking y writing).

| # | Atributo             | Tipo de dato | Longitud | Nulo | PK | UK | FK | Descripción                                      |
|---|----------------------|--------------|----------|------|----|----|----|--------------------------------------------------|
| 1 | idEvaluacion         | BIGINT       | —        | No   | Sí | —  | —  | Identificador único de la evaluación             |
| 2 | calificacionSpeaking | NUMERIC      | (5,2)    | No   | —  | —  | —  | Calificación de expresión oral (0–100)           |
| 3 | calificacionWriting  | NUMERIC      | (5,2)    | No   | —  | —  | —  | Calificación de expresión escrita (0–100)        |
| 4 | promedioFinal        | NUMERIC      | (5,2)    | No   | —  | —  | —  | Promedio de speaking y writing                   |
| 5 | idInscripcion        | BIGINT       | —        | No   | —  | Sí | Sí | Inscripción evaluada → **Inscripcion.idInscripcion** (1:1) |

---

## 6. Relaciones entre entidades

| Relación                    | Cardinalidad | Descripción                                              |
|----------------------------|--------------|----------------------------------------------------------|
| Profesor — Curso           | 1 : N        | Un profesor imparte uno o varios cursos                  |
| Alumno — Inscripcion       | 1 : N        | Un alumno puede tener varias inscripciones               |
| Curso — Inscripcion        | 1 : N        | Un curso puede tener varios alumnos inscritos            |
| Inscripcion — Evaluacion   | 1 : 1        | Cada inscripción tiene como máximo una evaluación        |

---

## 7. Reglas de negocio relevantes

- `boleta` y `correo` del alumno deben ser únicos en el sistema.
- `numEmpleado` del profesor debe ser único.
- Cada evaluación está ligada a una sola inscripción (`idInscripcion` UNIQUE).
- El `promedioFinal` se calcula en la aplicación como media de speaking y writing.
- Al registrar una inscripción, el sistema puede enviar correo de confirmación y generar constancia PDF.

---

## 8. Referencias del proyecto

| Recurso              | Ubicación                                              |
|----------------------|--------------------------------------------------------|
| Script SQL           | `docs/schema.sql`                                      |
| Modelo E-R           | `docs/MODELO_ER.md`                                    |
| Repositorio backend  | https://github.com/Alexa1611/celex-api                 |
| Repositorio frontend | https://github.com/Alexa1611/celex-frontend            |
| API desplegada       | https://celex-api.onrender.com/swagger-ui.html         |
| Frontend desplegado  | https://celex-frontend.onrender.com                    |
