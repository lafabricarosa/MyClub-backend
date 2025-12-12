package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.DisponibilidadDTO;
import com.gestiondeportiva.api.entities.Disponibilidad;
import com.gestiondeportiva.api.entities.EstadoDisponibilidad;
import com.gestiondeportiva.api.mappers.DisponibilidadMapper;
import com.gestiondeportiva.api.repositories.DisponibilidadRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de gestión de disponibilidad de jugadores para eventos.
 * <p>
 * Proporciona la lógica de negocio para gestionar la disponibilidad de los jugadores
 * para eventos, permitiendo registrar si asisten, no asisten o tienen dudas.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see DisponibilidadService
 */
@Service
@Transactional
public class DisponibilidadServiceImpl implements DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;
    private final DisponibilidadMapper disponibilidadMapper;

    public DisponibilidadServiceImpl(DisponibilidadRepository disponibilidadRepository,
            DisponibilidadMapper disponibilidadMapper) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.disponibilidadMapper = disponibilidadMapper;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<DisponibilidadDTO> findAll() {
        return disponibilidadMapper.toDTOList(disponibilidadRepository.findAll());
    }

    @Override
    @Transactional
    public DisponibilidadDTO save(DisponibilidadDTO disponibilidadDTO) {
        if (disponibilidadDTO == null) {
            throw new IllegalArgumentException("La disponibilidad no puede ser nula");
        }

        if (disponibilidadDTO.getIdEvento() == null) {
            throw new IllegalArgumentException("Debe indicar el evento asociado a la disponibilidad");
        }

        if (disponibilidadDTO.getIdJugador() == null) {
            throw new IllegalArgumentException("Debe indicar el jugador asociado a la disponibilidad");
        }

        if (disponibilidadDTO.getEstadoDisponibilidad() == null) {
            throw new IllegalArgumentException("Debe indicar el estado de la disponibilidad");
        }

        Disponibilidad disponibilidad = disponibilidadMapper.toEntity(disponibilidadDTO);
        Disponibilidad guardada = disponibilidadRepository.save(disponibilidad);
        return disponibilidadMapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public DisponibilidadDTO update(Long id, DisponibilidadDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");
        }
        Disponibilidad disponibilidadDb = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disponibilidad no encontrada con ID: " + id));
        disponibilidadMapper.updateEntityFromDTO(datosActualizados, disponibilidadDb);

        Disponibilidad actualizada = disponibilidadRepository.save(disponibilidadDb);
        return disponibilidadMapper.toDTO(actualizada);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!disponibilidadRepository.existsById(id)) {
            throw new EntityNotFoundException("Disponibilidad no encontrada con ID: " + id);
        }
        disponibilidadRepository.deleteById(id);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<DisponibilidadDTO> findById(Long id) {
        return disponibilidadRepository.findById(id)
                .map(disponibilidad -> disponibilidadMapper.toDTO(disponibilidad));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisponibilidadDTO> findByJugadorId(Long jugadorId) {
        return disponibilidadMapper.toDTOList(disponibilidadRepository.findByJugadorId(jugadorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisponibilidadDTO> findByEventoId(Long eventoId) {
        return disponibilidadMapper.toDTOList(disponibilidadRepository.findByEventoId(eventoId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisponibilidadDTO> findByEventoIdAndEstadoDisponibilidad(Long eventoId,
            EstadoDisponibilidad estadoDisponibilidad) {
        return disponibilidadMapper
                .toDTOList(disponibilidadRepository.findByEventoIdAndEstadoDisponibilidad(eventoId, estadoDisponibilidad));
    }

}
