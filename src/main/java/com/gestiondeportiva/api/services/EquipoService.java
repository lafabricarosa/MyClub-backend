package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.EquipoDTO;
import com.gestiondeportiva.api.entities.Categoria;

/**
 * Servicio de lógica de negocio para la gestión de equipos deportivos.
 * <p>
 * Proporciona operaciones CRUD y consultas para equipos, organizados por categorías
 * (BENJAMINES, ALEVINES, INFANTILES, CADETES, JUVENILES). Opera con DTOs para
 * separar la capa de presentación de la capa de persistencia.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see EquipoDTO
 * @see EquipoServiceImpl
 */
public interface EquipoService {

    /**
     * Obtiene todos los equipos del sistema.
     *
     * @return lista de EquipoDTO con todos los equipos
     */
    List<EquipoDTO> findAll();

    /**
     * Crea un nuevo equipo.
     *
     * @param equipoDTO datos del equipo a crear
     * @return EquipoDTO con el equipo creado y su ID asignado
     */
    EquipoDTO save(EquipoDTO equipoDTO);

    /**
     * Actualiza un equipo existente.
     * <p>
     * Solo actualiza los campos proporcionados en el DTO. Los campos null
     * no modifican los valores existentes.
     * </p>
     *
     * @param id ID del equipo a actualizar
     * @param datosActualizados DTO con los datos a actualizar
     * @return EquipoDTO con el equipo actualizado
     */
    EquipoDTO update(Long id, EquipoDTO datosActualizados);

    /**
     * Elimina un equipo por su ID.
     *
     * @param id ID del equipo a eliminar
     */
    void deleteById(Long id);

    /**
     * Busca un equipo por su ID.
     *
     * @param id ID del equipo
     * @return Optional con el EquipoDTO si existe, Optional.empty() si no
     */
    Optional<EquipoDTO> findById(Long id);

    /**
     * Busca equipos por nombre.
     *
     * @param nombre nombre del equipo
     * @return lista de EquipoDTO que coinciden con el nombre
     */
    List<EquipoDTO> findByNombre(String nombre);

    /**
     * Busca equipos por categoría.
     *
     * @param categoria categoría del equipo (BENJAMINES, ALEVINES, INFANTILES, CADETES, JUVENILES)
     * @return lista de EquipoDTO de la categoría especificada
     */
    List<EquipoDTO> findByCategoria(Categoria categoria);
}
