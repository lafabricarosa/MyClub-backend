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

@Mapper(componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface DisponibilidadMapper {

    // ======= Entity → DTO =======
    @Mapping(target = "idJugador", source = "jugador.id")
    @Mapping(target = "nombreJugador", source = "jugador.nombre")
    @Mapping(target = "apellidos", source = "jugador.apellidos")
    @Mapping(target = "fotoUrl", source = "jugador.fotoUrl")
    @Mapping(target = "posicion", source = "jugador.posicion")
    @Mapping(target = "idEvento", source = "evento.id")
    @Mapping(target = "descripcionEvento", source = "evento.descripcion")
    @Mapping(target = "fechaEvento", source = "evento.fecha")
    DisponibilidadDTO toDTO(Disponibilidad disponibilidad);

    // ======= Lista de entidades → Lista de DTOs =======
    List<DisponibilidadDTO> toDTOList(List<Disponibilidad> disponibilidad);

    // ======= DTO → Entity =======
    @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento")
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    Disponibilidad toEntity(DisponibilidadDTO dto);

    // ======= Actualizar entidad existente con datos del DTO (flexible) =======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento")
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    void updateEntityFromDTO(DisponibilidadDTO dto, @MappingTarget Disponibilidad entity);

    // ======= Métodos auxiliares =======
    @Named("mapEvento")
    default Evento mapEvento(Long id) {
        if (id == null)
            return null;
        Evento evento = new Evento();
        evento.setId(id);
        return evento;
    }

    @Named("mapJugador")
    default Usuario mapJugador(Long id) {
        if (id == null)
            return null;
        Usuario jugador = new Usuario();
        jugador.setId(id);
        return jugador;
    }
}