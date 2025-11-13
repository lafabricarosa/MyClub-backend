package com.gestiondeportiva.api.mappers;

import java.util.List;

import org.mapstruct.*;

import com.gestiondeportiva.api.dto.EquipoDTO;
import com.gestiondeportiva.api.entities.Equipo;
import com.gestiondeportiva.api.entities.Usuario;

@Mapper(componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface EquipoMapper {

    // ======= Entity → DTO =======
    @Mapping(target = "idEntrenador", source = "entrenador.id")
    @Mapping(target = "nombreEntrenador", source = "entrenador.nombre")
    EquipoDTO toDTO(Equipo equipo);

    // ======= Lista de entidades → Lista de DTOs =======
    List<EquipoDTO> toDTOList(List<Equipo> equipos);

    // ======= DTO → Entity =======
    @Mapping(source = "idEntrenador", target = "entrenador", qualifiedByName = "mapEntrenador")
    Equipo toEntity(EquipoDTO dto);

    // ======= Actualizar entidad existente con datos del DTO (flexible) =======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idEntrenador", target = "entrenador", qualifiedByName = "mapEntrenador")
    void updateEntityFromDTO(EquipoDTO dto, @MappingTarget Equipo equipo);

    // ======= Métodos auxiliares =======
    @Named("mapEntrenador")
    default Usuario mapEntrenador(Long id) {
        if (id == null)
            return null;
        Usuario entrenador = new Usuario();
        entrenador.setId(id);
        return entrenador;
    }
}