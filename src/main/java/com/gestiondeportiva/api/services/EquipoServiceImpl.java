package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.EquipoDTO;
import com.gestiondeportiva.api.entities.Categoria;
import com.gestiondeportiva.api.entities.Equipo;
import com.gestiondeportiva.api.mappers.EquipoMapper;
import com.gestiondeportiva.api.repositories.EquipoRepository;
import com.gestiondeportiva.api.security.SecurityUtils;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de gestión de equipos deportivos.
 * <p>
 * Proporciona la lógica de negocio para equipos con validaciones y control de acceso.
 * Los entrenadores solo pueden editar sus propios equipos.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see EquipoService
 */
@Service
@Transactional
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final EquipoMapper equipoMapper;
    private final SecurityUtils securityUtils;

    public EquipoServiceImpl(EquipoRepository equipoRepository, EquipoMapper equipoMapper,
            SecurityUtils securityUtils) {
        this.equipoRepository = equipoRepository;
        this.equipoMapper = equipoMapper;
        this.securityUtils = securityUtils;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<EquipoDTO> findAll() {
        return equipoMapper.toDTOList(equipoRepository.findAll());
    }

    @Override
    @Transactional
    public EquipoDTO save(EquipoDTO equipoDTO) {
        if (equipoDTO == null) {
            throw new IllegalArgumentException("El equipo no puede ser nulo");
        }

        if (equipoDTO.getNombre() == null) {
            throw new IllegalArgumentException("Debe indicar el nombre del equipo");
        }

        Equipo equipo = equipoMapper.toEntity(equipoDTO);
        Equipo guardado = equipoRepository.save(equipo);
        return equipoMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public EquipoDTO update(Long id, EquipoDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");
        }

        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + id));

        String emailActual = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        // Verificar si el equipo tiene entrenador antes de comparar
        if (equipo.getEntrenador() != null && !equipo.getEntrenador().getEmail().equals(emailActual)) {
            throw new AccessDeniedException("No puedes editar equipos de otro entrenador");
        }

        equipoMapper.updateEntityFromDTO(datosActualizados, equipo);
        Equipo actualizado = equipoRepository.save(equipo);
        return equipoMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipo no encontrado con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipoDTO> findById(Long id) {
        return equipoRepository.findById(id).map(equipo -> equipoMapper.toDTO(equipo));

    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoDTO> findByNombre(String nombre) {
        return equipoMapper.toDTOList(equipoRepository.findByNombre(nombre));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoDTO> findByCategoria(Categoria categoria) {
        return equipoMapper.toDTOList(equipoRepository.findByCategoria(categoria));
    }

}
