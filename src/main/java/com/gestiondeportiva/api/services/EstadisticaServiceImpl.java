package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.EstadisticaDTO;
import com.gestiondeportiva.api.entities.Estadistica;
import com.gestiondeportiva.api.mappers.EstadisticaMapper;
import com.gestiondeportiva.api.repositories.EstadisticaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class EstadisticaServiceImpl implements EstadisticaService {

    private final EstadisticaRepository estadisticaRepository;
    private final EstadisticaMapper estadisticaMapper;

    public EstadisticaServiceImpl(EstadisticaRepository estadisticaRepository, EstadisticaMapper estadisticaMapper) {
        this.estadisticaRepository = estadisticaRepository;
        this.estadisticaMapper = estadisticaMapper;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<EstadisticaDTO> findAll() {
        return estadisticaMapper.toDTOList(estadisticaRepository.findAll());
    }

    @Override
    @Transactional
    public EstadisticaDTO save(EstadisticaDTO estadisticaDTO) {
        if (estadisticaDTO == null) {
            throw new IllegalArgumentException("La estadística no puede ser nula");
        }

        if (estadisticaDTO.getIdEvento() == null) {
            throw new IllegalArgumentException("Debe especificar el evento asociado a la estadística");
        }

        if (estadisticaDTO.getIdJugador() == null) {
            throw new IllegalArgumentException("Debe especificar el jugador asociado a la estadística");
        }

        if (estadisticaDTO.getGoles() == null) {
            throw new IllegalArgumentException("Debe indicar la cantidad de goles");
        }

        if (estadisticaDTO.getTarjetasAmarillas() == null) {
            throw new IllegalArgumentException("Debe indicar la cantidad de tarjetas amarillas");
        }

        if (estadisticaDTO.getTarjetasRojas() == null) {
            throw new IllegalArgumentException("Debe indicar la cantidad de tarjetas rojas");
        }

        Estadistica estadistica = estadisticaMapper.toEntity(estadisticaDTO);
        Estadistica guardada = estadisticaRepository.save(estadistica);
        return estadisticaMapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public EstadisticaDTO update(Long id, EstadisticaDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");
        }
        Estadistica estadistica = estadisticaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estadística no encontrada con ID: " + id));
                
        estadisticaMapper.updateEntityFromDTO(datosActualizados, estadistica);
        Estadistica actualizada = estadisticaRepository.save(estadistica);
        return estadisticaMapper.toDTO(actualizada);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!estadisticaRepository.existsById(id)) {
            throw new EntityNotFoundException("Estadística no encontrada con el ID: " + id);
        }
        estadisticaRepository.deleteById(id);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadisticaDTO> findById(Long id) {
        return estadisticaRepository.findById(id)
                .map(estadistica -> estadisticaMapper.toDTO(estadistica));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadisticaDTO> findByJugadorId(Long jugadorId) {
        return estadisticaMapper.toDTOList(estadisticaRepository.findByJugadorId(jugadorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadisticaDTO> findByEventoId(Long eventoId) {
        return estadisticaMapper.toDTOList(estadisticaRepository.findByEventoId(eventoId));
    }
}
