package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.*;

import com.gestiondeportiva.api.entities.Estadistica;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.dto.EstadisticaDTO;

/**
 * Mapper de MapStruct para la conversión entre entidad Estadistica y EstadisticaDTO.
 * <p>
 * Maneja las conversiones de estadísticas de rendimiento de jugadores en eventos,
 * incluyendo goles y tarjetas. Resuelve relaciones con jugador y evento.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Estadistica
 * @see EstadisticaDTO
 */
@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface EstadisticaMapper {

    /**
     * Convierte una entidad Estadistica a EstadisticaDTO.
     * <p>
     * Desnormaliza los datos del jugador y evento asociados.
     * </p>
     *
     * @param estadistica entidad Estadistica a convertir
     * @return EstadisticaDTO con los datos de la estadística
     */
    @Mapping(target = "idJugador", source = "jugador.id")
    @Mapping(target = "nombreJugador", source = "jugador.nombre")
    @Mapping(target = "idEvento", source = "evento.id")
    @Mapping(target = "descripcionEvento", source = "evento.descripcion")
    EstadisticaDTO toDTO(Estadistica estadistica);

    /**
     * Convierte una lista de entidades Estadistica a lista de DTOs.
     *
     * @param estadisticas lista de entidades Estadistica
     * @return lista de EstadisticaDTO
     */
    List<EstadisticaDTO> toDTOList(List<Estadistica> estadisticas);

    /**
     * Convierte un EstadisticaDTO a entidad Estadistica.
     *
     * @param estadisticaDTO EstadisticaDTO con los datos
     * @return entidad Estadistica con relaciones JPA establecidas
     */
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento")
    Estadistica toEntity(EstadisticaDTO estadisticaDTO);

    /**
     * Actualiza una entidad Estadistica existente con datos del DTO.
     * <p>
     * Solo actualiza campos no nulos del DTO.
     * </p>
     *
     * @param dto EstadisticaDTO con los datos a actualizar
     * @param entity entidad Estadistica existente que será modificada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    @Mapping(source = "idEvento", target = "evento", qualifiedByName = "mapEvento")
    void updateEntityFromDTO(EstadisticaDTO dto, @MappingTarget Estadistica entity);

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
}