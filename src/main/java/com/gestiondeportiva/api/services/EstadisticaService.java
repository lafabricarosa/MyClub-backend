package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.EstadisticaDTO;

/**
 * Servicio de lógica de negocio para la gestión de estadísticas de jugadores.
 * <p>
 * Gestiona las estadísticas individuales de rendimiento de jugadores en eventos
 * (goles, tarjetas amarillas y rojas). Implementa lógica de upsert para evitar
 * estadísticas duplicadas del mismo jugador en el mismo evento. Opera con DTOs
 * para separar la capa de presentación de la capa de persistencia.
 * </p>
 *
 * <p><strong>Lógica de Upsert:</strong></p>
 * <ul>
 *   <li>Al guardar estadísticas, verifica si ya existen para el jugador y evento</li>
 *   <li>Si existen: actualiza los valores existentes</li>
 *   <li>Si no existen: crea un nuevo registro</li>
 *   <li>Evita duplicados y mantiene integridad de datos</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see EstadisticaDTO
 * @see EstadisticaServiceImpl
 */
public interface EstadisticaService {


    /**
     * Obtiene todas las estadísticas del sistema.
     *
     * @return lista de EstadisticaDTO con todas las estadísticas
     */
    List<EstadisticaDTO> findAll();

    /**
     * Crea una nueva estadística con lógica de upsert.
     * <p>
     * <strong>Comportamiento de Upsert:</strong>
     * <ul>
     *   <li>Verifica si ya existe una estadística para el jugador en el evento</li>
     *   <li>Si existe: actualiza los valores de goles y tarjetas</li>
     *   <li>Si no existe: crea una nueva estadística</li>
     * </ul>
     * Esta lógica previene duplicados y mantiene la integridad referencial.
     * </p>
     *
     * @param estadisticaDTO datos de la estadística a crear
     * @return EstadisticaDTO con la estadística creada y su ID asignado
     */
    EstadisticaDTO save(EstadisticaDTO estadisticaDTO);

    /**
     * Actualiza una estadística existente.
     * <p>
     * Solo actualiza los campos proporcionados en el DTO. Los campos null
     * no modifican los valores existentes.
     * </p>
     *
     * @param id ID de la estadística a actualizar
     * @param datosActualizados DTO con los datos a actualizar
     * @return EstadisticaDTO con la estadística actualizada
     */
    EstadisticaDTO update(Long id, EstadisticaDTO datosActualizados);

    /**
     * Elimina una estadística por su ID.
     *
     * @param id ID de la estadística a eliminar
     */
    void deleteById(Long id);

    /**
     * Busca todas las estadísticas de un jugador específico.
     *
     * @param jugadorId ID del jugador
     * @return lista de EstadisticaDTO del jugador
     */
    List<EstadisticaDTO> findByJugadorId(Long jugadorId);

    /**
     * Busca todas las estadísticas de un evento específico.
     *
     * @param eventoId ID del evento
     * @return lista de EstadisticaDTO del evento
     */
    List<EstadisticaDTO> findByEventoId(Long eventoId);

    /**
     * Busca una estadística por su ID.
     *
     * @param id ID de la estadística
     * @return Optional con el EstadisticaDTO si existe, Optional.empty() si no
     */
    Optional<EstadisticaDTO> findById(Long id);


}
