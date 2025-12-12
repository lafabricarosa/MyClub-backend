package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.*;
import com.gestiondeportiva.api.dto.ConvocatoriaDTO;
import com.gestiondeportiva.api.entities.Convocatoria;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.Usuario;

/**
 * Mapper de MapStruct para la conversión entre entidad Convocatoria y ConvocatoriaDTO.
 * <p>
 * Gestiona las conversiones de convocatorias de jugadores para eventos, indicando
 * si son titulares o suplentes.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Convocatoria
 * @see ConvocatoriaDTO
 */
@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface ConvocatoriaMapper {

    /**
     * Convierte una entidad Convocatoria a ConvocatoriaDTO.
     * <p>
     * Desnormaliza los datos del evento y del jugador convocado.
     * </p>
     *
     * @param convocatoria entidad Convocatoria a convertir
     * @return ConvocatoriaDTO con los datos de la convocatoria
     */
    @Mappings({
            @Mapping(target = "idEvento", source = "evento.id"),
            @Mapping(target = "descripcionEvento", source = "evento.descripcion"),
            @Mapping(target = "idJugador", source = "jugador.id"),
            @Mapping(target = "nombreJugador", source = "jugador.nombre")
    })
    ConvocatoriaDTO toDTO(Convocatoria convocatoria);

    /**
     * Convierte una lista de entidades Convocatoria a lista de ConvocatoriaDTO.
     *
     * @param convocatorias lista de entidades Convocatoria
     * @return lista de ConvocatoriaDTO
     */
    List<ConvocatoriaDTO> toDTOList(List<Convocatoria> convocatorias);

    /**
     * Convierte un ConvocatoriaDTO a entidad Convocatoria.
     *
     * @param convocatoriaDTO ConvocatoriaDTO con los datos
     * @return entidad Convocatoria con relaciones JPA establecidas
     */
    @Mappings({
            @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento"),
            @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    })
    Convocatoria toEntity(ConvocatoriaDTO convocatoriaDTO);

    /**
     * Actualiza una entidad Convocatoria existente con datos del DTO.
     * <p>
     * Solo actualiza campos no nulos del DTO.
     * </p>
     *
     * @param dto ConvocatoriaDTO con los datos a actualizar
     * @param entity entidad Convocatoria existente que será modificada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento"),
            @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    })
    void updateEntityFromDTO(ConvocatoriaDTO dto, @MappingTarget Convocatoria entity);

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
