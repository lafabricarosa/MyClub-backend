package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.DisponibilidadDTO;
import com.gestiondeportiva.api.entities.EstadoDisponibilidad;

public interface DisponibilidadService {

    List<DisponibilidadDTO> findAll();

    DisponibilidadDTO save(DisponibilidadDTO disponibilidadDTO);

    DisponibilidadDTO update(Long id, DisponibilidadDTO disponibilidadDTO);

    void deleteById(Long id);

    Optional<DisponibilidadDTO> findById(Long id);

    List<DisponibilidadDTO> findByJugadorId(Long jugadorId);

    List<DisponibilidadDTO> findByEventoId(Long eventoId);

    List<DisponibilidadDTO> findByEventoIdAndEstadoDisponibilidad(Long eventoId, EstadoDisponibilidad estadoDisponibilidad);
}
