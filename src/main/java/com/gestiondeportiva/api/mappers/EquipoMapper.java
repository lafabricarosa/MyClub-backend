package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.*;

import com.gestiondeportiva.api.dto.EquipoDTO;
import com.gestiondeportiva.api.entities.Equipo;
import com.gestiondeportiva.api.entities.Usuario;

/**
 * Mapper de MapStruct para la conversión entre entidad Equipo y EquipoDTO.
 * <p>
 * Maneja las conversiones de equipos deportivos y su relación con el entrenador asignado.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Equipo
 * @see EquipoDTO
 */
@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface EquipoMapper {

    /**
     * Convierte una entidad Equipo a EquipoDTO.
     * <p>
     * Desnormaliza el ID y nombre del entrenador en el DTO.
     * </p>
     *
     * @param equipo entidad Equipo a convertir
     * @return EquipoDTO con los datos del equipo
     */
    @Mapping(target = "idEntrenador", source = "entrenador.id")
    @Mapping(target = "nombreEntrenador", source = "entrenador.nombre")
    EquipoDTO toDTO(Equipo equipo);

    /**
     * Convierte una lista de entidades Equipo a lista de EquipoDTO.
     *
     * @param equipos lista de entidades Equipo
     * @return lista de EquipoDTO
     */
    List<EquipoDTO> toDTOList(List<Equipo> equipos);

    /**
     * Convierte un EquipoDTO a entidad Equipo.
     *
     * @param dto EquipoDTO con los datos del equipo
     * @return entidad Equipo con relaciones JPA establecidas
     */
    @Mapping(source = "idEntrenador", target = "entrenador", qualifiedByName = "mapEntrenador")
    Equipo toEntity(EquipoDTO dto);

    /**
     * Actualiza una entidad Equipo existente con datos del DTO.
     * <p>
     * Solo actualiza campos no nulos del DTO.
     * </p>
     *
     * @param dto EquipoDTO con los datos a actualizar
     * @param equipo entidad Equipo existente que será modificada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idEntrenador", target = "entrenador", qualifiedByName = "mapEntrenador")
    void updateEntityFromDTO(EquipoDTO dto, @MappingTarget Equipo equipo);

    /**
     * Método auxiliar para mapear un ID de entrenador a entidad Usuario proxy.
     *
     * @param id ID del entrenador
     * @return entidad Usuario proxy, o null si el ID es null
     */
    @Named("mapEntrenador")
    default Usuario mapEntrenador(Long id) {
        if (id == null)
            return null;
        Usuario entrenador = new Usuario();
        entrenador.setId(id);
        return entrenador;
    }
}