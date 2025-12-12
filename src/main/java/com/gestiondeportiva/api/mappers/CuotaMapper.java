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

import com.gestiondeportiva.api.dto.CuotaDTO;
import com.gestiondeportiva.api.entities.Cuota;
import com.gestiondeportiva.api.entities.Usuario;

/**
 * Mapper de MapStruct para la conversión entre entidad Cuota y CuotaDTO.
 * <p>
 * Facilita la gestión de cuotas de pago de los jugadores, convirtiendo entre
 * entidades JPA y DTOs para la API REST.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Cuota
 * @see CuotaDTO
 */
@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface CuotaMapper {

    /**
     * Convierte una entidad Cuota a CuotaDTO.
     * <p>
     * Desnormaliza el ID y nombre del jugador asociado.
     * </p>
     *
     * @param cuota entidad Cuota a convertir
     * @return CuotaDTO con los datos de la cuota
     */
    @Mapping(target = "idJugador", source = "jugador.id")
    @Mapping(target = "nombreJugador", source = "jugador.nombre")
    CuotaDTO toDTO(Cuota cuota);

    /**
     * Convierte una lista de entidades Cuota a lista de CuotaDTO.
     *
     * @param cuotas lista de entidades Cuota
     * @return lista de CuotaDTO
     */
    List<CuotaDTO> toDTOList(List<Cuota> cuotas);

    /**
     * Convierte un CuotaDTO a entidad Cuota.
     *
     * @param cuotaDTO CuotaDTO con los datos de la cuota
     * @return entidad Cuota con relaciones JPA establecidas
     */
    @Mapping(target = "jugador", source = "idJugador", qualifiedByName = "mapJugador")
    Cuota toEntity(CuotaDTO cuotaDTO);

    /**
     * Actualiza una entidad Cuota existente con datos del DTO.
     * <p>
     * Solo actualiza campos no nulos del DTO.
     * </p>
     *
     * @param cuotaDTO CuotaDTO con los datos a actualizar
     * @param cuota entidad Cuota existente que será modificada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    void updateEntityFromDTO(CuotaDTO cuotaDTO, @MappingTarget Cuota cuota);

    /**
     * Método auxiliar para mapear un ID de jugador a entidad Usuario proxy.
     *
     * @param id ID del jugador
     * @return entidad Usuario proxy, o null si el ID es null
     */
    @Named("mapJugador")
    default Usuario mapJugador(Long id) {
        if (id == null) return null;
        Usuario jugador = new Usuario();
        jugador.setId(id);
        return jugador;
    }
}

