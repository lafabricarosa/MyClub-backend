package com.gestiondeportiva.api.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un evento deportivo en el sistema.
 * <p>
 * Un evento puede ser de tres tipos:
 * <ul>
 *   <li>{@code ENTRENAMIENTO}: Sesión de práctica del equipo</li>
 *   <li>{@code PARTIDO}: Encuentro competitivo</li>
 *   <li>{@code REUNION}: Reunión del equipo</li>
 * </ul>
 * </p>
 *
 * <p>Los eventos están asociados a un equipo específico y contienen información sobre
 * la fecha, hora y lugar. Los jugadores pueden registrar su disponibilidad para cada
 * evento, y el entrenador puede crear convocatorias y registrar estadísticas.</p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see TipoEvento
 * @see Equipo
 * @see Convocatoria
 * @see Estadistica
 * @see Disponibilidad
 */
@Entity
@Table(name = "eventos")
public class Evento {

    /** Identificador único del evento */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Equipo al que pertenece el evento */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", nullable = false)
    @JsonIgnoreProperties("eventos")
    private Equipo equipo;

    /** Tipo de evento (ENTRENAMIENTO, PARTIDO, REUNION) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipoEvento;

    /** Fecha del evento */
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /** Hora del evento */
    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    /** Lugar donde se realiza el evento */
    private String lugar;

    /** Descripción o detalles adicionales del evento */
    private String descripcion;

    /** Estadísticas de los jugadores en este evento */
    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("evento")
    private Set<Estadistica> estadisticas;

    /** Disponibilidades de los jugadores para este evento */
    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("evento")
    private Set<Disponibilidad> disponibilidades;

    /** Convocatorias de jugadores para este evento */
    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("evento")
    private Set<Convocatoria> convocatorias;

    /**
     * Constructor por defecto que inicializa las colecciones vacías.
     */
    public Evento() {
        this.estadisticas = new HashSet<>();
        this.disponibilidades = new HashSet<>();
        this.convocatorias = new HashSet<>();
    }

    /**
     * Constructor con todos los campos principales.
     *
     * @param equipo Equipo al que pertenece el evento
     * @param tipoEvento Tipo de evento
     * @param fecha Fecha del evento
     * @param hora Hora del evento
     * @param lugar Lugar del evento
     * @param descripcion Descripción del evento
     */
    public Evento(Equipo equipo, TipoEvento tipoEvento, LocalDate fecha, LocalTime hora, String lugar, String descripcion) {
        this();
        this.equipo = equipo;
        this.tipoEvento = tipoEvento;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Estadistica> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Set<Estadistica> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public Set<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(Set<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public Set<Convocatoria> getConvocatorias() {
        return convocatorias;
    }

    public void setConvocatorias(Set<Convocatoria> convocatorias) {
        this.convocatorias = convocatorias;
    }

    /*public void addDisponibilidad(Disponibilidad disponibilidad) {
        disponibilidades.add(disponibilidad);
        disponibilidad.setEvento(this);
    }

    public void removeDisponibilidad(Disponibilidad disponibilidad) {
        disponibilidades.remove(disponibilidad);
        disponibilidad.setEvento(null);
    }

    public Estadistica addEstadistica(Estadistica estadistica) {
        estadisticas.add(estadistica);
        estadistica.setEvento(this);
        return estadistica;
    }

    public void removeEstadistica(Estadistica estadistica) {
        estadisticas.remove(estadistica);
        estadistica.setEvento(null);
    }*/

    @Override
    public String toString() {
        return "Evento{id=" + id +
                ", tipo=" + tipoEvento +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", lugar='" + lugar + '\'' +
                ", equipo=" + (equipo != null ? equipo.getNombre() : "N/A") + 
                ", disponibilidades=" + (disponibilidades != null ? disponibilidades.size() : 0) +
                ", convocatorias=" + (convocatorias != null ? convocatorias.size() : 0) +"}";
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
        Evento other = (Evento) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
