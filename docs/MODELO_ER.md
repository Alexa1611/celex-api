# Modelo Entidad-Relación (E-R) — Proyecto CELEX

**Proyecto:** Sistema de Gestión de Cursos de Idiomas — CELEX (IPN)  
**Base de datos:** YugabyteDB  

---

## Diagrama E-R

```mermaid
erDiagram
    PROFESOR ||--o{ CURSO : imparte
    ALUMNO ||--o{ INSCRIPCION : realiza
    CURSO ||--o{ INSCRIPCION : recibe
    INSCRIPCION ||--|| EVALUACION : tiene

    PROFESOR {
        bigint idProfesor PK
        varchar numEmpleado UK
        varchar nombre
        varchar apellidos
        varchar idiomaEspecialidad
    }

    ALUMNO {
        bigint idAlumno PK
        bigint boleta UK
        varchar nombre
        varchar apellidos
        varchar correo UK
    }

    CURSO {
        bigint idCurso PK
        varchar idioma
        varchar nivel
        varchar horario
        varchar salon
        bigint idProfesor FK
    }

    INSCRIPCION {
        bigint idInscripcion PK
        date fechaInscripcion
        varchar periodo
        bigint idAlumno FK
        bigint idCurso FK
    }

    EVALUACION {
        bigint idEvaluacion PK
        numeric calificacionSpeaking
        numeric calificacionWriting
        numeric promedioFinal
        bigint idInscripcion FK_UK
    }
```

---

## Descripción de entidades

| Entidad      | Descripción breve                                              |
|-------------|-----------------------------------------------------------------|
| **PROFESOR**    | Docente del CELEX con especialidad en un idioma                 |
| **ALUMNO**      | Estudiante del IPN registrado para tomar cursos                 |
| **CURSO**       | Oferta de un idioma (nivel, horario, salón, profesor)           |
| **INSCRIPCION** | Vincula un alumno con un curso en un periodo                    |
| **EVALUACION**  | Calificaciones oral y escrita de una inscripción (relación 1:1) |

---

## Cardinalidades

```
Profesor (1) ──────< (N) Curso
Alumno   (1) ──────< (N) Inscripcion
Curso    (1) ──────< (N) Inscripcion
Inscripcion (1) ────|| (1) Evaluacion
```

| Relación                 | Tipo        | Significado                                      |
|--------------------------|-------------|--------------------------------------------------|
| Profesor → Curso         | 1 : N       | Un profesor imparte muchos cursos                |
| Alumno → Inscripcion     | 1 : N       | Un alumno puede inscribirse a varios cursos      |
| Curso → Inscripcion      | 1 : N       | Un curso recibe varias inscripciones             |
| Inscripcion → Evaluacion | 1 : 1       | Cada inscripción tiene una evaluación asociada  |

---

## Diagrama simplificado (ASCII)

```
  ┌──────────┐         ┌──────────┐
  │ Profesor │ 1     N  │  Curso   │
  └────┬─────┘─────────└────┬─────┘
       │ imparte             │
       │                     │ recibe
       │                     │
  ┌────┴─────┐         ┌─────┴────────┐
  │  Alumno  │ 1     N │ Inscripcion  │
  └────┬─────┘─────────└─────┬────────┘
       │ realiza             │ 1
       │                     │
       │                     │ 1
       │               ┌─────┴────────┐
       │               │  Evaluacion    │
       │               └────────────────┘
       └────────────────────────────────── (via Inscripcion)
```

---

## Notas para la entrega

- Puedes exportar el diagrama **mermaid** desde [mermaid.live](https://mermaid.live) como imagen PNG para tu reporte.
- El script de implementación física está en `docs/schema.sql`.
- El detalle de atributos está en `docs/DICCIONARIO_DE_DATOS.md`.
