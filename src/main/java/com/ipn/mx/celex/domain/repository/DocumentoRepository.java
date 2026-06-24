package com.ipn.mx.celex.domain.repository;

import com.ipn.mx.celex.domain.entities.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByInscripcion_IdInscripcionOrderByFechaSubidaDesc(Long idInscripcion);
}
