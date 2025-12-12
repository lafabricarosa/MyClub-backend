package com.gestiondeportiva.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Convocatoria;

import java.util.List;

/**
 * Repositorio JPA para la gestión de entidades Convocatoria.
 * <p>
 * Gestiona las convocatorias de jugadores para eventos, distinguiendo entre
 * titulares y suplentes.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Convocatoria
 * @see JpaRepository
 */
public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Long> {

    /**
     * Busca todas las convocatorias de un evento específico.
     *
     * @param eventoId ID del evento
     * @return lista de convocatorias del evento
     */
    List<Convocatoria> findByEventoId(Long eventoId);

    /**
     * Busca todas las convocatorias de un jugador específico.
     *
     * @param jugadorId ID del jugador
     * @return lista de convocatorias del jugador
     */
    List<Convocatoria> findByJugadorId(Long jugadorId);

    /**
     * Busca solo los jugadores titulares convocados para un evento.
     * <p>
     * Filtra las convocatorias donde el campo titular es true.
     * </p>
     *
     * @param eventoId ID del evento
     * @return lista de convocatorias titulares del evento
     */
    List<Convocatoria> findByEventoIdAndTitularTrue(Long eventoId);

}

    