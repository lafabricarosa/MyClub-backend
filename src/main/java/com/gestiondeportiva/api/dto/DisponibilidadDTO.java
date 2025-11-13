package com.gestiondeportiva.api.dto;

import java.time.LocalDate;

import com.gestiondeportiva.api.entities.EstadoDisponibilidad;

public class DisponibilidadDTO {

    private Long id;
    private Long idJugador;
    private String nombreJugador;
    private Long idEvento;
    private String descripcionEvento;
    private LocalDate fechaEvento;
    private EstadoDisponibilidad estadoDisponibilidad;

    public DisponibilidadDTO() {
    }

    public DisponibilidadDTO(Long id, Long idJugador, String nombreJugador,
                             Long idEvento, String descripcionEvento,
                             LocalDate fechaEvento, EstadoDisponibilidad estadoDisponibilidad) {
        this.id = id;
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
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
