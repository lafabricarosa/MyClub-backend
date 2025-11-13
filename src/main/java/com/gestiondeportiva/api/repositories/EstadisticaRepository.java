package com.gestiondeportiva.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Estadistica;

public interface EstadisticaRepository extends JpaRepository<Estadistica, Long> {

    List<Estadistica> findByJugadorId(Long jugadorId);
    
    List<Estadistica> findByEventoId(Long eventoId);

}
