package com.gestiondeportiva.api.mappers;

import org.mapstruct.*;
import java.util.List;

import com.gestiondeportiva.api.entities.Equipo;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.dto.UsuarioDTO;
import com.gestiondeportiva.api.dto.UsuarioCreateDTO;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
        
public interface UsuarioMapper {

    // ======= Entity → DTO =======
    @Mapping(target = "idEquipo", source = "equipo.id")
    @Mapping(target = "nombreEquipo", source = "equipo.nombre")
    UsuarioDTO toDTO(Usuario entity);

    // ======= Lista de entidades → Lista de DTOs =======
    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    // ======= DTO → Entity =======
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    Usuario toEntity(UsuarioDTO dto);

    // ======= CreateDTO → Entity (para registro de usuarios) =======
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    Usuario toEntity(UsuarioCreateDTO dto);

    // ======= Actualizar entidad existente con datos del DTO (flexible) =======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idEquipo", target = "equipo", qualifiedByName = "mapEquipo")
    void updateEntityFromDTO(UsuarioDTO dto, @MappingTarget Usuario entity);

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
