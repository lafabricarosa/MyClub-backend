package com.gestiondeportiva.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.EventoDTO;
import com.gestiondeportiva.api.entities.TipoEvento;

public interface EventoService {

    List<EventoDTO> findAll();

    EventoDTO save(EventoDTO eventoDTO);

    EventoDTO update(Long id, EventoDTO datosActualizados);

    void deleteById(Long id);

    Optional<EventoDTO> findById(Long id);

    List<EventoDTO> findByEquipoId(Long equipoId);

    List<EventoDTO> findByFechaAfter(LocalDate fecha);

    List<EventoDTO> findByTipo(TipoEvento tipoEvento);

    List<EventoDTO> findByFecha(LocalDate fecha);

    List<EventoDTO> findByLugar(String lugar);

}
