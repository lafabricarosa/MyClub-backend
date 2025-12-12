package com.gestiondeportiva.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad JPA que representa la disponibilidad de un jugador para asistir a un evento.
 * <p>
 * Los jugadores pueden indicar si pueden asistir a un evento (partido, entrenamiento
 * o reunión) mediante tres estados posibles:
 * <ul>
 *   <li>{@code ASISTE}: El jugador confirma su asistencia</li>
 *   <li>{@code NO_ASISTE}: El jugador no puede asistir</li>
 *   <li>{@code DUDA}: El jugador aún no está seguro de su asistencia</li>
 * </ul>
 * </p>
 *
 * <p>Opcionalmente, el jugador puede añadir un comentario explicando su disponibilidad
 * (ej: "Tengo examen", "Lesionado", "Llegaré tarde").</p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Evento
 * @see Usuario
 * @see EstadoDisponibilidad
 */
@Entity
@Table(name = "disponibilidades")
public class Disponibilidad {

    /** Identificador único de la disponibilidad */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Evento para el que se registra la disponibilidad */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    @JsonIgnoreProperties("disponibilidades")
    @NotNull(message = "Debe indicar el evento")
    private Evento evento;

    /** Jugador que registra su disponibilidad */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    @JsonIgnoreProperties("disponibilidades")
    @NotNull(message = "Debe indicar el jugador")
    private Usuario jugador;

    /** Estado de disponibilidad del jugador (ASISTE, NO_ASISTE, DUDA) */
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Debe indicar la disponibilidad")
    private EstadoDisponibilidad estadoDisponibilidad;

    /** Comentario opcional del jugador sobre su disponibilidad */
    private String comentario;

    /**
     * Constructor por defecto.
     */
    public Disponibilidad() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param evento Evento para el que se registra la disponibilidad
     * @param jugador Jugador que registra su disponibilidad
     * @param estadoDisponibilidad Estado de disponibilidad
     * @param comentario Comentario opcional
     */
    public Disponibilidad(Evento evento, Usuario jugador, EstadoDisponibilidad estadoDisponibilidad,
            String comentario) {
        this.evento = evento;
        this.jugador = jugador;
        this.estadoDisponibilidad = estadoDisponibilidad;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getJugador() {
        return jugador;
    }

    public void setJugador(Usuario jugador) {
        this.jugador = jugador;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public EstadoDisponibilidad getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    @Override
    public String toString() {
        return "Disponibilidad{id=" + id +
                ", evento=" + (evento != null ? evento.getDescripcion() : "N/A") +
                ", jugador=" + (jugador != null ? jugador.getNombre() : "N/A") +
                ", estado=" + estadoDisponibilidad +
                ", comentario='" + comentario + "'}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Disponibilidad other = (Disponibilidad) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
