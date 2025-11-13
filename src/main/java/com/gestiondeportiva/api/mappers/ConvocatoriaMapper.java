package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.*;
import com.gestiondeportiva.api.dto.ConvocatoriaDTO;
import com.gestiondeportiva.api.entities.Convocatoria;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.Usuario;

@Mapper(componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    
public interface ConvocatoriaMapper {

    // ======= Entity → DTO =======
    @Mappings({
            @Mapping(target = "idEvento", source = "evento.id"),
            @Mapping(target = "descripcionEvento", source = "evento.descripcion"),
            @Mapping(target = "idJugador", source = "jugador.id"),
            @Mapping(target = "nombreJugador", source = "jugador.nombre")
    })
    ConvocatoriaDTO toDTO(Convocatoria convocatoria);

    // ======= Lista de entidades → Lista de DTOs =======
    List<ConvocatoriaDTO> toDTOList(List<Convocatoria> convocatorias);

    // ======= DTO → Entity =======
    @Mappings({
            @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento"),
            @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    })
    Convocatoria toEntity(ConvocatoriaDTO convocatoriaDTO);

    // ======= Actualizar entidad existente con datos del DTO (flexible) =======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento"),
            @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    })
    void updateEntityFromDTO(ConvocatoriaDTO dto, @MappingTarget Convocatoria entity);

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
