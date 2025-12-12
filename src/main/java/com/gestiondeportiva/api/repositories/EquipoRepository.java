package com.gestiondeportiva.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Categoria;
import com.gestiondeportiva.api.entities.Equipo;

/**
 * Repositorio JPA para la gestión de entidades Equipo.
 * <p>
 * Proporciona operaciones CRUD automáticas y consultas personalizadas para
 * gestionar equipos deportivos en el sistema.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Equipo
 * @see JpaRepository
 */
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    /**
     * Busca equipos por nombre.
     *
     * @param nombre nombre del equipo
     * @return lista de equipos que coinciden con el nombre
     */
    List<Equipo> findByNombre(String nombre);

    /**
     * Busca equipos por categoría.
     *
     * @param categoriaEnum categoría del equipo (BENJAMINES, ALEVINES, INFANTILES, CADETES, JUVENILES)
     * @return lista de equipos de la categoría especificada
     */
    List<Equipo> findByCategoria(Categoria categoriaEnum);

}
