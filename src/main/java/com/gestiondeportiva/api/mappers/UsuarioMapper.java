package com.gestiondeportiva.api.mappers;

import org.mapstruct.*;
import java.util.List;

import com.gestiondeportiva.api.entities.Equipo;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.dto.UsuarioDTO;
import com.gestiondeportiva.api.dto.UsuarioCreateDTO;

/**
 * Mapper de MapStruct para la conversión bidireccional entre entidad Usuario y sus DTOs.
 * <p>
 * MapStruct genera automáticamente las implementaciones de estos métodos en tiempo de compilación,
 * eliminando la necesidad de código boilerplate manual. Este mapper maneja la conversión entre
 * objetos de entidad JPA y objetos de transferencia de datos (DTOs) para la API REST.
 * </p>
 *
 * <p><strong>Funcionalidades principales:</strong></p>
 * <ul>
 *   <li>Conversión de Usuario a UsuarioDTO (desnormalizando datos del equipo)</li>
 *   <li>Conversión de UsuarioCreateDTO a Usuario (para registro de nuevos usuarios)</li>
 *   <li>Conversión de UsuarioDTO a Usuario (con resolución de relaciones JPA)</li>
 *   <li>Actualización parcial de usuarios existentes (solo campos no nulos)</li>
 *   <li>Conversión de listas completas de usuarios</li>
 * </ul>
 *
 * <p><strong>Configuración de MapStruct:</strong></p>
 * <ul>
 *   <li><code>componentModel = "spring"</code>: Registra el mapper como bean de Spring</li>
 *   <li><code>unmappedTargetPolicy = IGNORE</code>: Ignora campos destino sin mapping</li>
 *   <li><code>nullValueMappingStrategy = RETURN_NULL</code>: Retorna null si la fuente es null</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Usuario
 * @see UsuarioDTO
 * @see UsuarioCreateDTO
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface UsuarioMapper {

    /**
     * Convierte una entidad Usuario a su representación DTO.
     * <p>
     * Desnormaliza los datos del equipo asociado, incluyendo su ID y nombre
     * directamente en el DTO para evitar consultas adicionales en el frontend.
     * </p>
     *
     * @param entity entidad Usuario a convertir
     * @return UsuarioDTO con los datos del usuario (sin contraseña)
     */
    @Mapping(target = "idEquipo", source = "equipo.id")
    @Mapping(target = "nombreEquipo", source = "equipo.nombre")
    UsuarioDTO toDTO(Usuario entity);

    /**
     * Convierte una lista de entidades Usuario a una lista de DTOs.
     *
     * @param usuarios lista de entidades Usuario
     * @return lista de UsuarioDTO
     */
    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    /**
     * Convierte un UsuarioDTO a entidad Usuario.
     * <p>
     * Resuelve la relación con Equipo mediante el método auxiliar mapEquipo.
     * </p>
     *
     * @param dto UsuarioDTO con los datos del usuario
     * @return entidad Usuario con las relaciones JPA establecidas
     */
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    Usuario toEntity(UsuarioDTO dto);

    /**
     * Convierte un UsuarioCreateDTO a entidad Usuario para registro de nuevos usuarios.
     * <p>
     * Utilizado en la creación de usuarios. La contraseña debe ser encriptada
     * por el servicio antes de persistir la entidad.
     * </p>
     *
     * @param dto UsuarioCreateDTO con los datos del nuevo usuario
     * @return entidad Usuario lista para ser persistida (tras encriptar contraseña)
     */
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    Usuario toEntity(UsuarioCreateDTO dto);

    /**
     * Actualiza una entidad Usuario existente con datos de un UsuarioDTO.
     * <p>
     * Solo actualiza los campos que no son null en el DTO, permitiendo
     * actualizaciones parciales sin sobrescribir campos existentes con null.
     * </p>
     *
     * @param dto UsuarioDTO con los datos a actualizar
     * @param entity entidad Usuario existente que será modificada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    void updateEntityFromDTO(UsuarioDTO dto, @MappingTarget Usuario entity);

    /**
     * Método auxiliar para mapear un ID de equipo a una entidad Equipo proxy.
     * <p>
     * Crea una instancia de Equipo con solo el ID establecido. JPA utilizará
     * esta referencia para gestionar la relación sin cargar el equipo completo.
     * </p>
     *
     * @param id ID del equipo
     * @return entidad Equipo proxy con el ID establecido, o null si el ID es null
     */
    @Named("mapEquipo")
    default Equipo mapEquipo(Long id) {
        if (id == null)
            return null;
        Equipo equipo = new Equipo();
        equipo.setId(id);
        return equipo;
    }
}
