package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.*;

import com.gestiondeportiva.api.entities.Equipo;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.dto.EventoDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventoMapper {

    // ======= Entity → DTO =======
    @Mapping(target = "idEquipo", source = "equipo.id")
    @Mapping(target = "nombreEquipo", source = "equipo.nombre")
    EventoDTO toDTO(Evento evento);

    // ======= Lista de entidades → Lista de DTOs =======
    List<EventoDTO> toDTOList(List<Evento> eventos);

    // ======= DTO → Entity =======
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    Evento toEntity(EventoDTO dto);

    // ======= Actualizar entidad existente con datos del DTO (flexible) =======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    void updateEntityFromDTO(EventoDTO dto, @MappingTarget Evento entity);

    // ======= Métodos auxiliares =======
    @Named("mapEquipo")
    default Equipo mapEquipo(Long id) {
        if (id == null)
            return null;
        Equipo equipo = new Equipo();
        equipo.setId(id);
        return equipo;
    }
}
