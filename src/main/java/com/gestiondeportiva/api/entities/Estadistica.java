package com.gestiondeportiva.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
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
 * Entidad JPA que representa las estadísticas de un jugador en un evento específico.
 * <p>
 * Registra el rendimiento individual de un jugador durante un partido o entrenamiento,
 * incluyendo goles marcados, tarjetas amarillas y tarjetas rojas recibidas.
 * </p>
 *
 * <p>El sistema implementa lógica de <strong>upsert</strong> (update or insert) para
 * evitar duplicados: si ya existe una estadística para un jugador en un evento,
 * se actualiza; si no existe, se crea una nueva.</p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Evento
 * @see Usuario
 */
@Entity
@Table(name = "estadisticas")
public class Estadistica {

    /** Identificador único de la estadística */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Evento en el que se registró la estadística */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    @JsonIgnoreProperties("estadisticas")
    @NotNull(message = "Debe indicar el evento asociado")
    private Evento evento;

    /** Jugador al que pertenece la estadística */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    @JsonIgnoreProperties("estadisticas")
    @NotNull(message = "Debe indicar el jugador asociado")
    private Usuario jugador;

    /** Número de goles marcados por el jugador */
    @Column(nullable = false)
    private int goles = 0;

    /** Número de tarjetas amarillas recibidas */
    @Column(name = "tarjetas_amarillas", nullable = false)
    private int tarjetasAmarillas = 0;

    /** Número de tarjetas rojas recibidas */
    @Column(name = "tarjetas_rojas", nullable = false)
    private int tarjetasRojas = 0;

    /**
     * Constructor por defecto.
     */
    public Estadistica() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param evento Evento asociado a la estadística
     * @param jugador Jugador asociado a la estadística
     * @param goles Número de goles marcados
     * @param tarjetasAmarillas Número de tarjetas amarillas
     * @param tarjetasRojas Número de tarjetas rojas
     */
    public Estadistica(Evento evento, Usuario jugador, Integer goles, Integer tarjetasAmarillas,
            Integer tarjetasRojas) {
        this.evento = evento;
        this.jugador = jugador;
        this.goles = goles;
        this.tarjetasAmarillas = tarjetasAmarillas;
        this.tarjetasRojas = tarjetasRojas;
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

    public Integer getGoles() {
        return goles;
    }

    public void setGoles(Integer goles) {
        this.goles = goles;
    }

    public Integer getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public void setTarjetasAmarillas(Integer tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas;
    }

    public Integer getTarjetasRojas() {
        return tarjetasRojas;
    }

    public void setTarjetasRojas(Integer tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas;
    }

    @Override
    public String toString() {
        return "Estadistica{id=" + id +
                ", evento=" + (evento != null ? evento.getDescripcion() : "N/A") +
                ", jugador=" + (jugador != null ? jugador.getNombre() : "N/A") +
                ", goles=" + goles +
                ", tarjetasAmarillas=" + tarjetasAmarillas +
                ", tarjetasRojas=" + tarjetasRojas + "}";
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
        Estadistica other = (Estadistica) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
