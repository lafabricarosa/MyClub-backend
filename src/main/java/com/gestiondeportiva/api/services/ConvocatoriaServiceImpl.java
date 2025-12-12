package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.ConvocatoriaDTO;
import com.gestiondeportiva.api.entities.Convocatoria;
import com.gestiondeportiva.api.mappers.ConvocatoriaMapper;
import com.gestiondeportiva.api.repositories.ConvocatoriaRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de gestión de convocatorias de jugadores.
 * <p>
 * Proporciona la lógica de negocio para convocatorias, permitiendo
 * gestionar qué jugadores son convocados para eventos y si son titulares o suplentes.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see ConvocatoriaService
 */
@Service
@Transactional
public class ConvocatoriaServiceImpl implements ConvocatoriaService {

    private final ConvocatoriaRepository convocatoriaRepository;
    private final ConvocatoriaMapper convocatoriaMapper;

    public ConvocatoriaServiceImpl(ConvocatoriaRepository convocatoriaRepository,
            ConvocatoriaMapper convocatoriaMapper) {
        this.convocatoriaRepository = convocatoriaRepository;
        this.convocatoriaMapper = convocatoriaMapper;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<ConvocatoriaDTO> findAll() {
        return convocatoriaMapper.toDTOList(convocatoriaRepository.findAll());
    }

    @Override
    @Transactional
    public ConvocatoriaDTO save(ConvocatoriaDTO convocatoriaDTO) {
        if (convocatoriaDTO == null) {
            throw new IllegalArgumentException("La convocatoria no puede ser nula");
        }

        if (convocatoriaDTO.getIdEvento() == null) {
            throw new IllegalArgumentException("Debe indicar el evento asociado a la convocatoria");
        }

        if (convocatoriaDTO.getIdJugador() == null) {
            throw new IllegalArgumentException("Debe indicar el jugador asociado a la convocatoria");
        }

        Convocatoria convocatoria = convocatoriaMapper.toEntity(convocatoriaDTO);
        Convocatoria guardada = convocatoriaRepository.save(convocatoria);
        return convocatoriaMapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public ConvocatoriaDTO update(Long id, ConvocatoriaDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");
        }
        
        Convocatoria convocatoriaDb = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Convocatoria no encontrada con ID: " + id));
        convocatoriaMapper.updateEntityFromDTO(datosActualizados, convocatoriaDb);

        Convocatoria actualizada = convocatoriaRepository.save(convocatoriaDb);
        return convocatoriaMapper.toDTO(actualizada);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!convocatoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Convocatoria no encontrada con ID: " + id);
        }
        convocatoriaRepository.deleteById(id);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<ConvocatoriaDTO> findById(Long id) {
        return convocatoriaRepository.findById(id)
                .map(convocatoria -> convocatoriaMapper.toDTO(convocatoria));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConvocatoriaDTO> findByEventoId(Long eventoId) {
        return convocatoriaMapper.toDTOList(convocatoriaRepository.findByEventoId(eventoId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConvocatoriaDTO> findByJugadorId(Long jugadorId) {
        return convocatoriaMapper.toDTOList(convocatoriaRepository.findByJugadorId(jugadorId));
    }

    @Override
    @Transactional
    public List<ConvocatoriaDTO> findByEventoIdAndTitularTrue(Long eventoId) {
        return convocatoriaMapper.toDTOList(convocatoriaRepository.findByEventoIdAndTitularTrue(eventoId));
    }

}
