package com.gestiondeportiva.api.dto;

import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;

public class UsuarioCreateDTO {
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private Rol rol;
    private Posicion posicion;
    private String telefono;
    private Long idEquipo;


    public UsuarioCreateDTO() {
    }


    public UsuarioCreateDTO(String nombre, String apellidos, String email, String password, Rol rol, Posicion posicion,
            String telefono, Long idEquipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.posicion = posicion;
        this.telefono = telefono;
        this.idEquipo = idEquipo;
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


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
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


    public Long getIdEquipo() {
        return idEquipo;
    }


    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    

    
}
