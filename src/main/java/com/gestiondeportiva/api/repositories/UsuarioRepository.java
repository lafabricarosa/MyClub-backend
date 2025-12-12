package com.gestiondeportiva.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;
import com.gestiondeportiva.api.entities.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la gestión de entidades Usuario.
 * <p>
 * Proporciona métodos CRUD automáticos heredados de JpaRepository y consultas
 * personalizadas mediante Spring Data JPA Query Methods. Utilizado para acceder
 * y manipular usuarios en la base de datos.
 * </p>
 *
 * <p><strong>Métodos heredados de JpaRepository incluyen:</strong></p>
 * <ul>
 *   <li>save, saveAll - Guardar usuarios</li>
 *   <li>findById, findAll - Consultar usuarios</li>
 *   <li>deleteById, delete, deleteAll - Eliminar usuarios</li>
 *   <li>count, existsById - Operaciones de conteo y existencia</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Usuario
 * @see JpaRepository
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuarios por apellidos.
     *
     * @param apellidos apellidos del usuario
     * @return lista de usuarios que coinciden con los apellidos
     */
    List<Usuario> findByApellidos(String apellidos);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * <p>
     * Utilizado principalmente en la autenticación y para validar que
     * el email sea único en el sistema.
     * </p>
     *
     * @param email dirección de correo electrónico del usuario
     * @return Optional con el usuario si existe, Optional.empty() si no
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca todos los usuarios con un rol específico.
     *
     * @param rol rol del usuario (ADMIN, ENTRENADOR, JUGADOR)
     * @return lista de usuarios con el rol especificado
     */
    List<Usuario> findByRol(Rol rol);

    /**
     * Busca todos los usuarios que pertenecen a un equipo específico.
     *
     * @param equipoId ID del equipo
     * @return lista de usuarios del equipo
     */
    List<Usuario> findByEquipoId(Long equipoId);

    /**
     * Busca todos los jugadores por su posición en el campo.
     *
     * @param posicion posición del jugador (PORTERO, DEFENSA, CENTROCAMPISTA, DELANTERO)
     * @return lista de jugadores con la posición especificada
     */
    List<Usuario> findByPosicion(Posicion posicion);

}
