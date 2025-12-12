package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.DisponibilidadDTO;
import com.gestiondeportiva.api.entities.EstadoDisponibilidad;

/**
 * Servicio de lógica de negocio para la gestión de disponibilidad de jugadores.
 * <p>
 * Proporciona operaciones CRUD y consultas para gestionar la disponibilidad
 * de jugadores para eventos (asiste, no asiste, duda).
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see DisponibilidadDTO
 */
public interface DisponibilidadService {

    /**
     * Obtiene todas las disponibilidades del sistema.
     *
     * @return lista de todas las disponibilidades
     */
    List<DisponibilidadDTO> findAll();

    /**
     * Crea una nueva disponibilidad.
     *
     * @param disponibilidadDTO datos de la nueva disponibilidad
     * @return DisponibilidadDTO de la disponibilidad creada
     */
    DisponibilidadDTO save(DisponibilidadDTO disponibilidadDTO);

    /**
     * Actualiza una disponibilidad existente.
     * <p>
     * Típicamente usado para que los jugadores cambien su estado de disponibilidad.
     * </p>
     *
     * @param id ID de la disponibilidad a actualizar
     * @param disponibilidadDTO datos a actualizar
     * @return DisponibilidadDTO con los datos actualizados
     */
    DisponibilidadDTO update(Long id, DisponibilidadDTO disponibilidadDTO);

    /**
     * Elimina una disponibilidad por su ID.
     *
     * @param id ID de la disponibilidad a eliminar
     */
    void deleteById(Long id);

    /**
     * Busca una disponibilidad por su ID.
     *
     * @param id ID de la disponibilidad
     * @return Optional con la disponibilidad si existe, Optional.empty() si no
     */
    Optional<DisponibilidadDTO> findById(Long id);

    /**
     * Busca todas las disponibilidades de un jugador.
     *
     * @param jugadorId ID del jugador
     * @return lista de disponibilidades del jugador
     */
    List<DisponibilidadDTO> findByJugadorId(Long jugadorId);

    /**
     * Busca todas las disponibilidades de un evento.
     *
     * @param eventoId ID del evento
     * @return lista de disponibilidades del evento
     */
    List<DisponibilidadDTO> findByEventoId(Long eventoId);

    /**
     * Busca disponibilidades de un evento filtradas por estado.
     * <p>
     * Permite obtener, por ejemplo, solo los jugadores que confirman asistencia.
     * </p>
     *
     * @param eventoId ID del evento
     * @param estadoDisponibilidad estado de disponibilidad (ASISTE, NO_ASISTE, DUDA)
     * @return lista de disponibilidades con el estado especificado
     */
    List<DisponibilidadDTO> findByEventoIdAndEstadoDisponibilidad(Long eventoId, EstadoDisponibilidad estadoDisponibilidad);
}
