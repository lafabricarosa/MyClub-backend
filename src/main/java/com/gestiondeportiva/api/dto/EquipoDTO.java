package com.gestiondeportiva.api.dto;

import com.gestiondeportiva.api.entities.Categoria;

public class EquipoDTO {

    private Long id;
    private String nombre;
    private Categoria categoria;
    private Long idEntrenador;
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

