package com.gestiondeportiva.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Disponibilidad;
import com.gestiondeportiva.api.entities.EstadoDisponibilidad;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    List<Disponibilidad> findByJugadorId(Long jugadorId);

    List<Disponibilidad> findByEventoId(Long eventoId);

    List<Disponibilidad> findByEventoIdAndEstadoDisponibilidad(Long id, EstadoDisponibilidad estadoDisponibilidad);
}
