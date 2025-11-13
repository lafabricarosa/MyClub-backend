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

@Entity
@Table(name = "estadisticas")
public class Estadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    @JsonIgnoreProperties("estadisticas")
    @NotNull(message = "Debe indicar el evento asociado")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    @JsonIgnoreProperties("estadisticas")
    @NotNull(message = "Debe indicar el jugador asociado")
    private Usuario jugador;

    @Column(nullable = false)
    private int goles = 0;

    @Column(name = "tarjetas_amarillas", nullable = false)
    private int tarjetasAmarillas = 0;

    @Column(name = "tarjetas_rojas", nullable = false)
    private int tarjetasRojas = 0;

    public Estadistica() {
    }

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
