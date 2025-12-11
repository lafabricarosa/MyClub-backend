package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.EstadisticaDTO;
import com.gestiondeportiva.api.entities.Estadistica;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.mappers.EstadisticaMapper;
import com.gestiondeportiva.api.repositories.EstadisticaRepository;
import com.gestiondeportiva.api.repositories.UsuarioRepository;
import com.gestiondeportiva.api.repositories.EventoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class EstadisticaServiceImpl implements EstadisticaService {

    private final EstadisticaRepository estadisticaRepository;
    private final EstadisticaMapper estadisticaMapper;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public EstadisticaServiceImpl(EstadisticaRepository estadisticaRepository,
                                   EstadisticaMapper estadisticaMapper,
                                   UsuarioRepository usuarioRepository,
                                   EventoRepository eventoRepository) {
        this.estadisticaRepository = estadisticaRepository;
        this.estadisticaMapper = estadisticaMapper;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
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

        // Buscar las entidades reales desde la base de datos
        Usuario jugador = usuarioRepository.findById(estadisticaDTO.getIdJugador())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Jugador no encontrado con ID: " + estadisticaDTO.getIdJugador()));

        Evento evento = eventoRepository.findById(estadisticaDTO.getIdEvento())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Evento no encontrado con ID: " + estadisticaDTO.getIdEvento()));

        // Crear la estadística con las entidades reales
        Estadistica estadistica = new Estadistica();
        estadistica.setJugador(jugador);
        estadistica.setEvento(evento);
        estadistica.setGoles(estadisticaDTO.getGoles());
        estadistica.setTarjetasAmarillas(estadisticaDTO.getTarjetasAmarillas());
        estadistica.setTarjetasRojas(estadisticaDTO.getTarjetasRojas());

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

        // Actualizar las relaciones si se proporcionan nuevos IDs
        if (datosActualizados.getIdJugador() != null) {
            Usuario jugador = usuarioRepository.findById(datosActualizados.getIdJugador())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Jugador no encontrado con ID: " + datosActualizados.getIdJugador()));
            estadistica.setJugador(jugador);
        }

        if (datosActualizados.getIdEvento() != null) {
            Evento evento = eventoRepository.findById(datosActualizados.getIdEvento())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Evento no encontrado con ID: " + datosActualizados.getIdEvento()));
            estadistica.setEvento(evento);
        }

        // Actualizar las estadísticas numéricas
        if (datosActualizados.getGoles() != null) {
            estadistica.setGoles(datosActualizados.getGoles());
        }
        if (datosActualizados.getTarjetasAmarillas() != null) {
            estadistica.setTarjetasAmarillas(datosActualizados.getTarjetasAmarillas());
        }
        if (datosActualizados.getTarjetasRojas() != null) {
            estadistica.setTarjetasRojas(datosActualizados.getTarjetasRojas());
        }

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
