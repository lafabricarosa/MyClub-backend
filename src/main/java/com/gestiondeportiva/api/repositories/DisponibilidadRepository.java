package com.gestiondeportiva.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Disponibilidad;
import com.gestiondeportiva.api.entities.EstadoDisponibilidad;

/**
 * Repositorio JPA para la gestión de entidades Disponibilidad.
 * <p>
 * Gestiona la disponibilidad de jugadores para eventos, permitiendo consultar
 * quién asiste, quién no asiste y quién tiene dudas sobre su asistencia.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Disponibilidad
 * @see JpaRepository
 */
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    /**
     * Busca todas las disponibilidades de un jugador específico.
     *
     * @param jugadorId ID del jugador
     * @return lista de disponibilidades del jugador
     */
    List<Disponibilidad> findByJugadorId(Long jugadorId);

    /**
     * Busca todas las disponibilidades de un evento específico.
     *
     * @param eventoId ID del evento
     * @return lista de disponibilidades del evento
     */
    List<Disponibilidad> findByEventoId(Long eventoId);

    /**
     * Busca disponibilidades de un evento filtradas por estado.
     * <p>
     * Permite obtener, por ejemplo, solo los jugadores que confirman asistencia
     * o solo aquellos que no pueden asistir a un evento específico.
     * </p>
     *
     * @param id ID del evento
     * @param estadoDisponibilidad estado de disponibilidad (ASISTE, NO_ASISTE, DUDA)
     * @return lista de disponibilidades con el estado especificado
     */
    List<Disponibilidad> findByEventoIdAndEstadoDisponibilidad(Long id, EstadoDisponibilidad estadoDisponibilidad);
}
