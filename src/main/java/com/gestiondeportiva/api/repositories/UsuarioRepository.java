package com.gestiondeportiva.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;
import com.gestiondeportiva.api.entities.Usuario;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByApellidos(String apellidos);

    Optional <Usuario> findByEmail(String email);

    List<Usuario> findByRol(Rol rol);

    List<Usuario> findByEquipoId(Long equipoId);

    List<Usuario> findByPosicion(Posicion posicion);

}
