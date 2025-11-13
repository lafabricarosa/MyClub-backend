package com.gestiondeportiva.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.EventoDTO;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.TipoEvento;
import com.gestiondeportiva.api.mappers.EventoMapper;
import com.gestiondeportiva.api.repositories.EventoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;

    public EventoServiceImpl(EventoRepository eventoRepository, EventoMapper eventoMapper) {
        this.eventoRepository = eventoRepository;
        this.eventoMapper = eventoMapper;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findAll() {
        return eventoMapper.toDTOList(eventoRepository.findAll());
    }

    @Override
    @Transactional
    public EventoDTO save(EventoDTO eventoDTO) {
        if (eventoDTO == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo");
        }
        if (eventoDTO.getIdEquipo() == null) {
            throw new IllegalArgumentException("Debe especificar el equipo del evento");
        }
        if (eventoDTO.getTipoEvento() == null) {
            throw new IllegalArgumentException("Debe indicar el tipo de evento");
        }
        if (eventoDTO.getFecha() == null) {
            throw new IllegalArgumentException("Debe indicar la fecha del evento");
        }
        if (eventoDTO.getHora() == null) {
            throw new IllegalArgumentException("Debe indicar la hora del evento");
        }

        Evento evento = eventoMapper.toEntity(eventoDTO);
        Evento guardado = eventoRepository.save(evento);
        return eventoMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public EventoDTO update(Long id, EventoDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");
        }
        Evento eventoDb = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con ID: " + id));

        eventoMapper.updateEntityFromDTO(datosActualizados, eventoDb);

        Evento actualizado = eventoRepository.save(eventoDb);
        return eventoMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe un evento con ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<EventoDTO> findById(Long id) {
        return eventoRepository.findById(id)
                .map(evento -> eventoMapper.toDTO(evento));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByEquipoId(Long equipoId) {
        return eventoMapper.toDTOList(eventoRepository.findByEquipoId(equipoId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByFechaAfter(LocalDate fecha) {
        return eventoMapper.toDTOList(eventoRepository.findByFechaAfter(fecha));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByTipo(TipoEvento tipoEvento) {
        return eventoMapper.toDTOList(eventoRepository.findByTipoEvento(tipoEvento));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByFecha(LocalDate fecha) {
        return eventoMapper.toDTOList(eventoRepository.findByFecha(fecha));

    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByLugar(String lugar) {
        return eventoMapper.toDTOList(eventoRepository.findByLugar(lugar));
    }

}
