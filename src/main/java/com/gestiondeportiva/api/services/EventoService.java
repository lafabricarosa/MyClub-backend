package com.gestiondeportiva.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.EventoDTO;
import com.gestiondeportiva.api.entities.TipoEvento;

/**
 * Servicio de lógica de negocio para la gestión de eventos deportivos.
 * <p>
 * Gestiona partidos, entrenamientos y otros eventos del club, permitiendo
 * consultas por tipo, fecha, lugar y equipo. Opera con DTOs para separar
 * la capa de presentación de la capa de persistencia.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see EventoDTO
 * @see EventoServiceImpl
 */
public interface EventoService {

    /**
     * Obtiene todos los eventos del sistema.
     *
     * @return lista de EventoDTO con todos los eventos
     */
    List<EventoDTO> findAll();

    /**
     * Crea un nuevo evento.
     *
     * @param eventoDTO datos del evento a crear
     * @return EventoDTO con el evento creado y su ID asignado
     */
    EventoDTO save(EventoDTO eventoDTO);

    /**
     * Actualiza un evento existente.
     * <p>
     * Solo actualiza los campos proporcionados en el DTO. Los campos null
     * no modifican los valores existentes.
     * </p>
     *
     * @param id ID del evento a actualizar
     * @param datosActualizados DTO con los datos a actualizar
     * @return EventoDTO con el evento actualizado
     */
    EventoDTO update(Long id, EventoDTO datosActualizados);

    /**
     * Elimina un evento por su ID.
     *
     * @param id ID del evento a eliminar
     */
    void deleteById(Long id);

    /**
     * Busca un evento por su ID.
     *
     * @param id ID del evento
     * @return Optional con el EventoDTO si existe, Optional.empty() si no
     */
    Optional<EventoDTO> findById(Long id);

    /**
     * Busca todos los eventos de un equipo específico.
     *
     * @param equipoId ID del equipo
     * @return lista de EventoDTO del equipo
     */
    List<EventoDTO> findByEquipoId(Long equipoId);

    /**
     * Busca eventos que ocurren después de una fecha específica.
     *
     * @param fecha fecha de referencia
     * @return lista de EventoDTO posteriores a la fecha especificada
     */
    List<EventoDTO> findByFechaAfter(LocalDate fecha);

    /**
     * Busca eventos por tipo.
     *
     * @param tipoEvento tipo de evento (PARTIDO, ENTRENAMIENTO)
     * @return lista de EventoDTO del tipo especificado
     */
    List<EventoDTO> findByTipo(TipoEvento tipoEvento);

    /**
     * Busca eventos que ocurren en una fecha específica.
     *
     * @param fecha fecha exacta del evento
     * @return lista de EventoDTO en la fecha especificada
     */
    List<EventoDTO> findByFecha(LocalDate fecha);

    /**
     * Busca eventos por lugar.
     *
     * @param lugar ubicación del evento
     * @return lista de EventoDTO en el lugar especificado
     */
    List<EventoDTO> findByLugar(String lugar);

}
