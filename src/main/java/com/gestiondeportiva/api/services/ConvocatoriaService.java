package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.ConvocatoriaDTO;

/**
 * Servicio de lógica de negocio para la gestión de convocatorias de jugadores.
 * <p>
 * Proporciona operaciones CRUD y consultas para convocatorias de jugadores
 * a eventos, distinguiendo entre titulares y suplentes.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see ConvocatoriaDTO
 */
public interface ConvocatoriaService {

    /**
     * Obtiene todas las convocatorias del sistema.
     *
     * @return lista de todas las convocatorias
     */
    List<ConvocatoriaDTO> findAll();

    /**
     * Crea una nueva convocatoria.
     *
     * @param convocatoriaDTO datos de la nueva convocatoria
     * @return ConvocatoriaDTO de la convocatoria creada
     */
    ConvocatoriaDTO save(ConvocatoriaDTO convocatoriaDTO);

    /**
     * Actualiza una convocatoria existente.
     *
     * @param id ID de la convocatoria a actualizar
     * @param datosActualizados datos a actualizar
     * @return ConvocatoriaDTO con los datos actualizados
     */
    ConvocatoriaDTO update(Long id, ConvocatoriaDTO datosActualizados);

    /**
     * Elimina una convocatoria por su ID.
     *
     * @param id ID de la convocatoria a eliminar
     */
    void deleteById(Long id);

    /**
     * Busca todas las convocatorias de un evento.
     *
     * @param eventoId ID del evento
     * @return lista de convocatorias del evento
     */
    List<ConvocatoriaDTO> findByEventoId(Long eventoId);

    /**
     * Busca todas las convocatorias de un jugador.
     *
     * @param jugadorId ID del jugador
     * @return lista de convocatorias del jugador
     */
    List<ConvocatoriaDTO> findByJugadorId(Long jugadorId);

    /**
     * Busca solo los jugadores titulares convocados para un evento.
     *
     * @param eventoId ID del evento
     * @return lista de convocatorias titulares del evento
     */
    List<ConvocatoriaDTO> findByEventoIdAndTitularTrue(Long eventoId);

    /**
     * Busca una convocatoria por su ID.
     *
     * @param id ID de la convocatoria
     * @return Optional con la convocatoria si existe, Optional.empty() si no
     */
    Optional<ConvocatoriaDTO> findById(Long id);

}

