package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.*;

import com.gestiondeportiva.api.entities.Equipo;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.dto.EventoDTO;

/**
 * Mapper de MapStruct para la conversión bidireccional entre entidad Evento y EventoDTO.
 * <p>
 * Facilita la conversión entre eventos del calendario deportivo (entrenamientos, partidos,
 * reuniones) y sus representaciones DTO para la API REST.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Evento
 * @see EventoDTO
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventoMapper {

    /**
     * Convierte una entidad Evento a EventoDTO.
     * <p>
     * Desnormaliza el nombre del equipo en el DTO para evitar consultas adicionales.
     * </p>
     *
     * @param evento entidad Evento a convertir
     * @return EventoDTO con los datos del evento
     */
    @Mapping(target = "idEquipo", source = "equipo.id")
    @Mapping(target = "nombreEquipo", source = "equipo.nombre")
    EventoDTO toDTO(Evento evento);

    /**
     * Convierte una lista de entidades Evento a lista de EventoDTO.
     *
     * @param eventos lista de entidades Evento
     * @return lista de EventoDTO
     */
    List<EventoDTO> toDTOList(List<Evento> eventos);

    /**
     * Convierte un EventoDTO a entidad Evento.
     *
     * @param dto EventoDTO con los datos del evento
     * @return entidad Evento con relaciones JPA establecidas
     */
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    Evento toEntity(EventoDTO dto);

    /**
     * Actualiza una entidad Evento existente con datos del DTO.
     * <p>
     * Solo actualiza campos no nulos del DTO.
     * </p>
     *
     * @param dto EventoDTO con los datos a actualizar
     * @param entity entidad Evento existente que será modificada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    void updateEntityFromDTO(EventoDTO dto, @MappingTarget Evento entity);

    /**
     * Método auxiliar para mapear un ID de equipo a entidad Equipo proxy.
     *
     * @param id ID del equipo
     * @return entidad Equipo proxy, o null si el ID es null
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
