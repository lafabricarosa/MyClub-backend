package com.gestiondeportiva.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.gestiondeportiva.api.entities.TipoEvento;

public class EventoDTO {

    private Long id;
    private TipoEvento tipoEvento;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private String lugar;
    private Long idEquipo;
    private String nombreEquipo;

    public EventoDTO() {
    }

    public EventoDTO(Long id, TipoEvento tipoEvento, String descripcion,
                     LocalDate fecha, LocalTime hora,
                     String lugar, Long idEquipo, String nombreEquipo) {
        this.id = id;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
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

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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
