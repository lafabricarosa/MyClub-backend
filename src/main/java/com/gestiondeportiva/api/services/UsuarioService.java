package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.UsuarioDTO;
import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;
import com.gestiondeportiva.api.dto.UsuarioCreateDTO;

public interface UsuarioService {

  
    List<UsuarioDTO> findAll();

    UsuarioDTO save(UsuarioCreateDTO nuevoUsuario);

    UsuarioDTO update(Long id, UsuarioDTO datosActualizados);

    void deleteById(Long id);

    Optional<UsuarioDTO> findById(Long id);

    Optional<UsuarioDTO> findByEmail(String email);

    List<UsuarioDTO> findByEquipoId(Long equipoId);

    List<UsuarioDTO> findByRol(Rol rol);

    List<UsuarioDTO> findByApellidos(String apellidos);

    List<UsuarioDTO> findByPosicion(Posicion posicion);
}


