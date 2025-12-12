package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.UsuarioDTO;
import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;
import com.gestiondeportiva.api.dto.UsuarioCreateDTO;

/**
 * Servicio de lógica de negocio para la gestión de usuarios del sistema.
 * <p>
 * Proporciona operaciones CRUD y funcionalidades específicas como autenticación,
 * cambio de contraseña, y consultas por rol, posición o equipo. Los usuarios pueden
 * ser administradores, entrenadores o jugadores. Opera con DTOs para separar la
 * capa de presentación de la capa de persistencia.
 * </p>
 *
 * <p><strong>Características principales:</strong></p>
 * <ul>
 *   <li>Gestión completa de usuarios (crear, leer, actualizar, eliminar)</li>
 *   <li>Autenticación mediante email</li>
 *   <li>Cambio seguro de contraseñas con BCrypt</li>
 *   <li>Consultas por rol (ADMIN, ENTRENADOR, JUGADOR)</li>
 *   <li>Consultas por posición de juego (para jugadores)</li>
 *   <li>Consultas por equipo</li>
 *   <li>Conversión automática entre entidades y DTOs</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see UsuarioDTO
 * @see UsuarioCreateDTO
 * @see UsuarioServiceImpl
 */
public interface UsuarioService {


    /**
     * Obtiene todos los usuarios del sistema.
     *
     * @return lista de UsuarioDTO con todos los usuarios
     */
    List<UsuarioDTO> findAll();

    /**
     * Crea un nuevo usuario en el sistema.
     * <p>
     * La contraseña se encripta automáticamente con BCrypt antes de guardar.
     * Se valida que el email sea único en el sistema.
     * </p>
     *
     * @param nuevoUsuario datos del nuevo usuario a crear
     * @return UsuarioDTO con el usuario creado y su ID asignado
     */
    UsuarioDTO save(UsuarioCreateDTO nuevoUsuario);

    /**
     * Actualiza un usuario existente.
     * <p>
     * Solo actualiza los campos proporcionados en el DTO. Los campos null
     * no modifican los valores existentes.
     * </p>
     *
     * @param id ID del usuario a actualizar
     * @param datosActualizados DTO con los datos a actualizar
     * @return UsuarioDTO con el usuario actualizado
     */
    UsuarioDTO update(Long id, UsuarioDTO datosActualizados);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar
     */
    void deleteById(Long id);

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Optional con el UsuarioDTO si existe, Optional.empty() si no
     */
    Optional<UsuarioDTO> findById(Long id);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * <p>
     * Utilizado principalmente en la autenticación del sistema.
     * </p>
     *
     * @param email dirección de correo electrónico del usuario
     * @return Optional con el UsuarioDTO si existe, Optional.empty() si no
     */
    Optional<UsuarioDTO> findByEmail(String email);

    /**
     * Busca todos los usuarios que pertenecen a un equipo específico.
     *
     * @param equipoId ID del equipo
     * @return lista de UsuarioDTO de los usuarios del equipo
     */
    List<UsuarioDTO> findByEquipoId(Long equipoId);

    /**
     * Busca todos los usuarios con un rol específico.
     *
     * @param rol rol del usuario (ADMIN, ENTRENADOR, JUGADOR)
     * @return lista de UsuarioDTO con el rol especificado
     */
    List<UsuarioDTO> findByRol(Rol rol);

    /**
     * Busca usuarios por apellidos.
     *
     * @param apellidos apellidos del usuario
     * @return lista de UsuarioDTO que coinciden con los apellidos
     */
    List<UsuarioDTO> findByApellidos(String apellidos);

    /**
     * Busca todos los jugadores por su posición en el campo.
     *
     * @param posicion posición del jugador (PORTERO, DEFENSA, CENTROCAMPISTA, DELANTERO)
     * @return lista de UsuarioDTO con la posición especificada
     */
    List<UsuarioDTO> findByPosicion(Posicion posicion);

    /**
     * Cambia la contraseña de un usuario.
     * <p>
     * Verifica que la contraseña actual sea correcta antes de establecer la nueva.
     * La nueva contraseña se encripta con BCrypt antes de guardar.
     * </p>
     *
     * @param id ID del usuario
     * @param passwordActual contraseña actual del usuario (para verificación)
     * @param passwordNueva nueva contraseña a establecer
     * @throws RuntimeException si la contraseña actual no es correcta o el usuario no existe
     */
    void cambiarPassword(Long id, String passwordActual, String passwordNueva);
}


