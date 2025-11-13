package com.gestiondeportiva.api.dto;

import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;

public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private Rol rol;
    private Posicion posicion;
    private String telefono;
    private String fotoUrl;
    private Long idEquipo;
    private String nombreEquipo;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String apellidos, String email,
                      Rol rol, Posicion posicion, String telefono,
                      String fotoUrl, Long idEquipo, String nombreEquipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.rol = rol;
        this.posicion = posicion;
        this.telefono = telefono;
        this.fotoUrl = fotoUrl;
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }
}
