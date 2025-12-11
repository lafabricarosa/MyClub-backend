package com.gestiondeportiva.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Estadistica;

public interface EstadisticaRepository extends JpaRepository<Estadistica, Long> {

    List<Estadistica> findByJugadorId(Long jugadorId);

    List<Estadistica> findByEventoId(Long eventoId);

    // Buscar estadística por evento y jugador (para evitar duplicados)
    Optional<Estadistica> findByEventoIdAndJugadorId(Long eventoId, Long jugadorId);

}
