package com.gestiondeportiva.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.gestiondeportiva.api.entities.TipoEvento;

/**
 * DTO (Data Transfer Object) para transferir datos de eventos en respuestas API.
 * <p>
 * Representa un evento del calendario deportivo (entrenamiento, partido o reunión)
 * con toda la información necesaria para su visualización. Incluye el nombre del
 * equipo desnormalizado para evitar consultas adicionales.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.entities.Evento
 */
public class EventoDTO {

    /** Identificador único del evento */
    private Long id;

    /** Tipo de evento (ENTRENAMIENTO, PARTIDO, REUNION) */
    private TipoEvento tipoEvento;

    /** Descripción detallada del evento */
    private String descripcion;

    /** Fecha del evento */
    private LocalDate fecha;

    /** Hora de inicio del evento */
    private LocalTime hora;

    /** Ubicación donde se realizará el evento */
    private String lugar;

    /** ID del equipo al que pertenece el evento */
    private Long idEquipo;

    /** Nombre del equipo (desnormalizado) */
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
