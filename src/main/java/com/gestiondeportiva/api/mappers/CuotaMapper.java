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

@Mapper(componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    
public interface CuotaMapper {

    // ======= Entity → DTO =======
    @Mapping(target = "idJugador", source = "jugador.id")
    @Mapping(target = "nombreJugador", source = "jugador.nombre")
    CuotaDTO toDTO(Cuota cuota);

    // ======= Lista de entidades → Lista de DTOs =======
    List<CuotaDTO> toDTOList(List<Cuota> cuotas);

    // ======= DTO → Entity =======
    @Mapping(target = "jugador", source = "idJugador", qualifiedByName = "mapJugador")
    Cuota toEntity(CuotaDTO cuotaDTO);

    // ======= Actualizar entidad existente con datos del DTO (flexible) =======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idJugador", target = "jugador", qualifiedByName = "mapJugador")
    void updateEntityFromDTO(CuotaDTO cuotaDTO, @MappingTarget Cuota cuota);

    // ======= Métodos auxiliares =======
    @Named("mapJugador")
    default Usuario mapJugador(Long id) {
        if (id == null) return null;
        Usuario jugador = new Usuario();
        jugador.setId(id);
        return jugador;
    }
}

