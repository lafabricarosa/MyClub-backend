package com.gestiondeportiva.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Convocatoria;

import java.util.List;


public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Long> {

    List<Convocatoria> findByEventoId(Long eventoId);
    
    List<Convocatoria> findByJugadorId(Long jugadorId);

    List<Convocatoria> findByEventoIdAndTitularTrue(Long eventoId);

}

    