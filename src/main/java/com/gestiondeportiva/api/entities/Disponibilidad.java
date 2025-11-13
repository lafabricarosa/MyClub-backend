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

@Entity
@Table(name = "disponibilidades")
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    @JsonIgnoreProperties("disponibilidades")
    @NotNull(message = "Debe indicar el evento")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    @JsonIgnoreProperties("disponibilidades")
    @NotNull(message = "Debe indicar el jugador")
    private Usuario jugador;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Debe indicar la disponibilidad")
    private EstadoDisponibilidad estadoDisponibilidad;

    private String comentario;

    public Disponibilidad() {
    }

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
