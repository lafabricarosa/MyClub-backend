package com.gestiondeportiva.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Estadistica;

/**
 * Repositorio JPA para la gestión de entidades Estadistica.
 * <p>
 * Gestiona el acceso a estadísticas de rendimiento de jugadores en eventos,
 * incluyendo goles y tarjetas. Implementa lógica para evitar duplicados
 * mediante la búsqueda combinada por evento y jugador.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Estadistica
 * @see JpaRepository
 */
public interface EstadisticaRepository extends JpaRepository<Estadistica, Long> {

    /**
     * Busca todas las estadísticas de un jugador específico.
     *
     * @param jugadorId ID del jugador
     * @return lista de estadísticas del jugador
     */
    List<Estadistica> findByJugadorId(Long jugadorId);

    /**
     * Busca todas las estadísticas de un evento específico.
     *
     * @param eventoId ID del evento
     * @return lista de estadísticas del evento
     */
    List<Estadistica> findByEventoId(Long eventoId);

    /**
     * Busca una estadística específica por evento y jugador.
     * <p>
     * Utilizado para implementar lógica de upsert y evitar la creación
     * de estadísticas duplicadas para el mismo jugador y evento.
     * </p>
     *
     * @param eventoId ID del evento
     * @param jugadorId ID del jugador
     * @return Optional con la estadística si existe, Optional.empty() si no
     */
    Optional<Estadistica> findByEventoIdAndJugadorId(Long eventoId, Long jugadorId);

}
