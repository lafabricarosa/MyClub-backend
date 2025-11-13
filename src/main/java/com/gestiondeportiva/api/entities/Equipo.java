package com.gestiondeportiva.api.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del equipo no puede estar vacío")
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToOne
    @JoinColumn(name = "entrenador_id")
    @JsonIgnoreProperties("equipo")
    private Usuario entrenador;

    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("equipo")
    private Set<Usuario> jugadores;

    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("equipo")
    private Set<Evento> eventos;

    public Equipo() {
        this.jugadores = new HashSet<>();
        this.eventos = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Usuario entrenador) {
        this.entrenador = entrenador;
    }

    public Set<Usuario> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Set<Usuario> jugadores) {
        this.jugadores = jugadores;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }

    /*public Usuario addJugador(Usuario jugador) {
        jugadores.add(jugador);
        jugador.setEquipo(this);
        return jugador;
    }

    public void removeJugador(Usuario jugador) {
        this.jugadores.remove(jugador);
        jugador.setEquipo(null);
    }

    public Evento addEvento(Evento evento) {
        eventos.add(evento);
        evento.setEquipo(this);
        return evento;
    }

    public void removeEvento(Evento evento) {
        this.eventos.remove(evento);
        evento.setEquipo(null);
    }*/

    @Override
    public String toString() {
        return "Equipo{id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", entrenador=" + (entrenador != null ? entrenador.getNombre() : "N/A") +
                ", jugadores=" + (jugadores != null ? jugadores.size() : 0) +
                ", eventos=" + (eventos != null ? eventos.size() : 0) +
                '}';
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Equipo other = (Equipo) obj;
        return id != null && id.equals(other.id);
    }

}
