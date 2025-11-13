package com.gestiondeportiva.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Categoria;
import com.gestiondeportiva.api.entities.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByNombre(String nombre);

    List<Equipo> findByCategoria(Categoria categoriaEnum);

}
