package com.gestiondeportiva.api.dto;

import com.gestiondeportiva.api.entities.Categoria;

/**
 * DTO (Data Transfer Object) para transferir datos de equipos en respuestas API.
 * <p>
 * Representa un equipo del sistema con su información básica y el entrenador asignado.
 * Incluye el nombre del entrenador desnormalizado para facilitar la visualización
 * sin necesidad de realizar consultas adicionales a la base de datos.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.entities.Equipo
 */
public class EquipoDTO {

    /** Identificador único del equipo */
    private Long id;

    /** Nombre del equipo */
    private String nombre;

    /** Categoría del equipo (BENJAMINES, ALEVINES, INFANTILES, CADETES, JUVENILES) */
    private Categoria categoria;

    /** ID del entrenador asignado al equipo */
    private Long idEntrenador;

    /** Nombre completo del entrenador (desnormalizado) */
    private String nombreEntrenador;

    public EquipoDTO() {
    }

    public EquipoDTO(Long id, String nombre, Categoria categoria, 
                     Long idEntrenador, String nombreEntrenador) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.idEntrenador = idEntrenador;
        this.nombreEntrenador = nombreEntrenador;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(Long idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getNombreEntrenador() {
        return nombreEntrenador;
    }

    public void setNombreEntrenador(String nombreEntrenador) {
        this.nombreEntrenador = nombreEntrenador;
    }
}

