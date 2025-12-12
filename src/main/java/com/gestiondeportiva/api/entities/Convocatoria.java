package com.gestiondeportiva.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad JPA que representa la convocatoria de un jugador para un evento.
 * <p>
 * Una convocatoria vincula a un jugador específico con un evento (partido, entrenamiento
 * o reunión) e indica si el jugador es titular o suplente.
 * </p>
 *
 * <p>El entrenador crea las convocatorias para cada evento, seleccionando qué jugadores
 * participarán y cuáles serán titulares.</p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Evento
 * @see Usuario
 */
@Entity
@Table(name = "convocatorias")
public class Convocatoria {

    /** Identificador único de la convocatoria */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Evento para el cual se convoca al jugador */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    @JsonIgnoreProperties("convocatorias")
    @NotNull(message = "Debe indicar el evento de la convocatoria")
    private Evento evento;

    /** Jugador convocado para el evento */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    @JsonIgnoreProperties("convocatorias")
    @NotNull(message = "Debe indicar el jugador convocado")
    private Usuario jugador;

    /** Indica si el jugador es titular (true) o suplente (false) */
    private Boolean titular;

    /**
     * Constructor por defecto.
     */
    public Convocatoria() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param evento Evento para el que se convoca
     * @param jugador Jugador convocado
     * @param titular Indica si es titular o suplente
     */
    public Convocatoria(Evento evento, Usuario jugador, Boolean titular) {
        this.evento = evento;
        this.jugador = jugador;
        this.titular = titular;
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

    public Boolean getTitular() {
        return titular;
    }

    public void setTitular(Boolean titular) {
        this.titular = titular;
    }

    @Override
    public String toString() {
        return "Convocatoria{id=" + id +
                ", evento=" + (evento != null ? evento.getDescripcion() : "N/A") +
                ", jugador=" + (jugador != null ? jugador.getNombre() : "N/A") +
                ", titular=" + titular + "}";
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
        Convocatoria other = (Convocatoria) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
