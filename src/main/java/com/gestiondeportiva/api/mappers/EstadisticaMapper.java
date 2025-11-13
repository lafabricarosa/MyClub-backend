package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.*;

import com.gestiondeportiva.api.entities.Estadistica;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.dto.EstadisticaDTO;

@Mapper(componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface EstadisticaMapper {

    // ======= Entity → DTO =======
    @Mapping(target = "idJugador", source = "jugador.id")
    @Mapping(target = "nombreJugador", source = "jugador.nombre")
    @Mapping(target = "idEvento", source = "evento.id")
    @Mapping(target = "descripcionEvento", source = "evento.descripcion")
    EstadisticaDTO toDTO(Estadistica estadistica);

    // ======= Lista de entidades → Lista de DTOs =======
    List<EstadisticaDTO> toDTOList(List<Estadistica> estadisticas);

    // ======= DTO → Entity =======
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento")
    Estadistica toEntity(EstadisticaDTO estadisticaDTO);

    // ======= Actualizar entidad existente con datos del DTO (flexible) =======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento")
    void updateEntityFromDTO(EstadisticaDTO dto, @MappingTarget Estadistica entity);

    // ======= Métodos auxiliares =======
    @Named("mapJugador")
    default Usuario mapJugador(Long id) {
        if (id == null)
            return null;
        Usuario jugador = new Usuario();
        jugador.setId(id);
        return jugador;
    }

    @Named("mapEvento")
    default Evento mapEvento(Long id) {
        if (id == null)
            return null;
        Evento evento = new Evento();
        evento.setId(id);
        return evento;
    }
}