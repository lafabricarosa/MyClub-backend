package com.gestiondeportiva.api.dto;

import java.time.LocalDate;

import com.gestiondeportiva.api.entities.EstadoDisponibilidad;
import com.gestiondeportiva.api.entities.Posicion;

public class DisponibilidadDTO {

    private Long id;
    private Long idJugador;
    private String nombreJugador;
    private String apellidos;
    private String fotoUrl;
    private Posicion posicion;
    private Long idEvento;
    private String descripcionEvento;
    private LocalDate fechaEvento;
    private EstadoDisponibilidad estadoDisponibilidad;

    public DisponibilidadDTO() {
    }

    public DisponibilidadDTO(Long id, Long idJugador, String nombreJugador, String apellidos,
                             String fotoUrl, Posicion posicion, Long idEvento, String descripcionEvento,
                             LocalDate fechaEvento, EstadoDisponibilidad estadoDisponibilidad) {
        this.id = id;
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.apellidos = apellidos;
        this.fotoUrl = fotoUrl;
        this.posicion = posicion;
        this.idEvento = idEvento;
        this.descripcionEvento = descripcionEvento;
        this.fechaEvento = fechaEvento;
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Long idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public EstadoDisponibilidad getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }
}
