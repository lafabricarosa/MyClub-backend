package com.gestiondeportiva.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.EventoDTO;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.TipoEvento;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.mappers.EventoMapper;
import com.gestiondeportiva.api.repositories.EventoRepository;
import com.gestiondeportiva.api.security.SecurityUtils;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;
    private final SecurityUtils securityUtils;

    public EventoServiceImpl(EventoRepository eventoRepository,
                             EventoMapper eventoMapper,
                             SecurityUtils securityUtils) {
        this.eventoRepository = eventoRepository;
        this.eventoMapper = eventoMapper;
        this.securityUtils = securityUtils;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findAll() {

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN → ve todos
        if (securityUtils.esAdminActual()) {
            return eventoMapper.toDTOList(eventoRepository.findAll());
        }

        // ENTRENADOR o JUGADOR → solo eventos de su equipo
        if (actual.getEquipo() == null) {
            throw new AccessDeniedException("No tienes equipo asignado para ver eventos");
        }

        return eventoMapper.toDTOList(
                eventoRepository.findByEquipoId(actual.getEquipo().getId())
        );
    }

    @Override
    @Transactional
    public EventoDTO save(EventoDTO eventoDTO) {

        if (eventoDTO == null)
            throw new IllegalArgumentException("El evento no puede ser nulo");

        if (eventoDTO.getIdEquipo() == null)
            throw new IllegalArgumentException("Debe especificar el equipo del evento");

        if (eventoDTO.getTipoEvento() == null)
            throw new IllegalArgumentException("Debe indicar el tipo de evento");

        if (eventoDTO.getFecha() == null)
            throw new IllegalArgumentException("Debe indicar la fecha del evento");

        if (eventoDTO.getHora() == null)
            throw new IllegalArgumentException("Debe indicar la hora del evento");

        Usuario actual = securityUtils.getUsuarioActual();

        // JUGADOR → jamás puede crear eventos
        if (securityUtils.esJugadorActual()) {
            throw new AccessDeniedException("Un jugador no puede crear eventos");
        }

        // ENTRENADOR → solo puede crear eventos de su equipo
        if (securityUtils.esEntrenadorActual()) {
            if (actual.getEquipo() == null ||
                !actual.getEquipo().getId().equals(eventoDTO.getIdEquipo())) {
                throw new AccessDeniedException("Solo puedes crear eventos de tu propio equipo");
            }
        }

        // ADMIN → sin restricciones

        Evento evento = eventoMapper.toEntity(eventoDTO);
        Evento guardado = eventoRepository.save(evento);
        return eventoMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public EventoDTO update(Long id, EventoDTO datosActualizados) {

        if (datosActualizados == null)
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");

        Evento eventoDb = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con ID: " + id));

        Usuario actual = securityUtils.getUsuarioActual();

        // JUGADOR → nunca puede modificar
        if (securityUtils.esJugadorActual()) {
            throw new AccessDeniedException("Un jugador no puede modificar eventos");
        }

        // ENTRENADOR → solo eventos de su equipo
        if (securityUtils.esEntrenadorActual()) {
            if (actual.getEquipo() == null ||
                eventoDb.getEquipo() == null ||
                !actual.getEquipo().getId().equals(eventoDb.getEquipo().getId())) {
                throw new AccessDeniedException("No puedes modificar eventos de otro equipo");
            }
        }

        // ADMIN → sin restricciones

        eventoMapper.updateEntityFromDTO(datosActualizados, eventoDb);

        Evento actualizado = eventoRepository.save(eventoDb);
        return eventoMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un evento con ID: " + id));

        Usuario actual = securityUtils.getUsuarioActual();

        // JUGADOR → nunca puede borrar
        if (securityUtils.esJugadorActual()) {
            throw new AccessDeniedException("Un jugador no puede eliminar eventos");
        }

        // ENTRENADOR → solo eventos de su equipo
        if (securityUtils.esEntrenadorActual()) {
            if (actual.getEquipo() == null ||
                evento.getEquipo() == null ||
                !actual.getEquipo().getId().equals(evento.getEquipo().getId())) {
                throw new AccessDeniedException("No puedes eliminar eventos de otro equipo");
            }
        }

        // ADMIN → sin restricciones

        eventoRepository.delete(evento);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<EventoDTO> findById(Long id) {

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN → ve todo
        if (securityUtils.esAdminActual()) {
            return Optional.of(eventoMapper.toDTO(evento));
        }

        // ENTRENADOR o JUGADOR → solo eventos de su equipo
        if (actual.getEquipo() == null ||
                evento.getEquipo() == null ||
                !actual.getEquipo().getId().equals(evento.getEquipo().getId())) {
            throw new AccessDeniedException("No puedes acceder a eventos de otro equipo");
        }

        return Optional.of(eventoMapper.toDTO(evento));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByEquipoId(Long equipoId) {

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN → sin restricciones
        if (securityUtils.esAdminActual()) {
            return eventoMapper.toDTOList(eventoRepository.findByEquipoId(equipoId));
        }

        // JUGADOR o ENTRENADOR → solo su equipo
        if (actual.getEquipo() == null ||
            !actual.getEquipo().getId().equals(equipoId)) {
            throw new AccessDeniedException("No puedes ver eventos de otros equipos");
        }

        return eventoMapper.toDTOList(eventoRepository.findByEquipoId(equipoId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByFechaAfter(LocalDate fecha) {

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN → sin restricciones
        if (securityUtils.esAdminActual()) {
            return eventoMapper.toDTOList(eventoRepository.findByFechaAfter(fecha));
        }

        // ENTRENADOR o JUGADOR → filtrar solo eventos de su equipo
        return eventoMapper.toDTOList(
                eventoRepository.findByFechaAfter(fecha).stream()
                        .filter(ev -> ev.getEquipo().getId().equals(actual.getEquipo().getId()))
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByTipo(TipoEvento tipoEvento) {

        Usuario actual = securityUtils.getUsuarioActual();

        if (securityUtils.esAdminActual()) {
            return eventoMapper.toDTOList(eventoRepository.findByTipoEvento(tipoEvento));
        }

        return eventoMapper.toDTOList(
                eventoRepository.findByTipoEvento(tipoEvento).stream()
                        .filter(ev -> ev.getEquipo().getId().equals(actual.getEquipo().getId()))
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByFecha(LocalDate fecha) {

        Usuario actual = securityUtils.getUsuarioActual();

        if (securityUtils.esAdminActual()) {
            return eventoMapper.toDTOList(eventoRepository.findByFecha(fecha));
        }

        return eventoMapper.toDTOList(
                eventoRepository.findByFecha(fecha).stream()
                        .filter(ev -> ev.getEquipo().getId().equals(actual.getEquipo().getId()))
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findByLugar(String lugar) {

        Usuario actual = securityUtils.getUsuarioActual();

        if (securityUtils.esAdminActual()) {
            return eventoMapper.toDTOList(eventoRepository.findByLugar(lugar));
        }

        return eventoMapper.toDTOList(
                eventoRepository.findByLugar(lugar).stream()
                        .filter(ev -> ev.getEquipo().getId().equals(actual.getEquipo().getId()))
                        .toList()
        );
    }

}

