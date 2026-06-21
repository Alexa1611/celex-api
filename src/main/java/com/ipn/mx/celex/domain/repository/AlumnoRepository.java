package com.ipn.mx.celex.domain.repository;

import com.ipn.mx.celex.domain.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}
