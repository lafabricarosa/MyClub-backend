package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.gestiondeportiva.api.dto.DisponibilidadDTO;
import com.gestiondeportiva.api.entities.Disponibilidad;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.Usuario;

/**
 * Mapper de MapStruct para la conversión entre entidad Disponibilidad y DisponibilidadDTO.
 * <p>
 * Gestiona las conversiones de disponibilidad de jugadores para eventos, incluyendo
 * información extendida del jugador (nombre, apellidos, foto, posición) y del evento.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Disponibilidad
 * @see DisponibilidadDTO
 */
@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface DisponibilidadMapper {

    /**
     * Convierte una entidad Disponibilidad a DisponibilidadDTO.
     * <p>
     * Desnormaliza múltiples campos del jugador (nombre, apellidos, foto, posición)
     * y del evento para una visualización completa sin consultas adicionales.
     * </p>
     *
     * @param disponibilidad entidad Disponibilidad a convertir
     * @return DisponibilidadDTO con información completa del jugador y evento
     */
    @Mapping(target = "idJugador", source = "jugador.id")
    @Mapping(target = "nombreJugador", source = "jugador.nombre")
    @Mapping(target = "apellidos", source = "jugador.apellidos")
    @Mapping(target = "fotoUrl", source = "jugador.fotoUrl")
    @Mapping(target = "posicion", source = "jugador.posicion")
    @Mapping(target = "idEvento", source = "evento.id")
    @Mapping(target = "descripcionEvento", source = "evento.descripcion")
    @Mapping(target = "fechaEvento", source = "evento.fecha")
    DisponibilidadDTO toDTO(Disponibilidad disponibilidad);

    /**
     * Convierte una lista de entidades Disponibilidad a lista de DisponibilidadDTO.
     *
     * @param disponibilidad lista de entidades Disponibilidad
     * @return lista de DisponibilidadDTO
     */
    List<DisponibilidadDTO> toDTOList(List<Disponibilidad> disponibilidad);

    /**
     * Convierte un DisponibilidadDTO a entidad Disponibilidad.
     *
     * @param dto DisponibilidadDTO con los datos
     * @return entidad Disponibilidad con relaciones JPA establecidas
     */
    @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento")
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    Disponibilidad toEntity(DisponibilidadDTO dto);

    /**
     * Actualiza una entidad Disponibilidad existente con datos del DTO.
     * <p>
     * Solo actualiza campos no nulos del DTO, útil para cambios de estado de disponibilidad.
     * </p>
     *
     * @param dto DisponibilidadDTO con los datos a actualizar
     * @param entity entidad Disponibilidad existente que será modificada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento")
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    void updateEntityFromDTO(DisponibilidadDTO dto, @MappingTarget Disponibilidad entity);

    /**
     * Método auxiliar para mapear un ID de evento a entidad Evento proxy.
     *
     * @param id ID del evento
     * @return entidad Evento proxy, o null si el ID es null
     */
    @Named("mapEvento")
    default Evento mapEvento(Long id) {
        if (id == null)
            return null;
        Evento evento = new Evento();
        evento.setId(id);
        return evento;
    }

    /**
     * Método auxiliar para mapear un ID de jugador a entidad Usuario proxy.
     *
     * @param id ID del jugador
     * @return entidad Usuario proxy, o null si el ID es null
     */
    @Named("mapJugador")
    default Usuario mapJugador(Long id) {
        if (id == null)
            return null;
        Usuario jugador = new Usuario();
        jugador.setId(id);
        return jugador;
    }
}