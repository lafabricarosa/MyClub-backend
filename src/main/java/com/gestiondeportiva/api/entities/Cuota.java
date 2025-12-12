package com.gestiondeportiva.api.entities;

import java.time.LocalDate;

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
 * Entidad JPA que representa una cuota de pago de un jugador.
 * <p>
 * Las cuotas permiten gestionar los pagos periódicos o puntuales de los jugadores,
 * como mensualidades, material deportivo, inscripciones a torneos, etc.
 * </p>
 *
 * <p>Cada cuota tiene un concepto, un importe y un estado que puede ser:
 * <ul>
 *   <li>{@code PENDIENTE}: Cuota sin pagar</li>
 *   <li>{@code PAGADO}: Cuota abonada (incluye fecha de pago)</li>
 *   <li>{@code EXENTO}: Jugador exento de pagar esta cuota</li>
 * </ul>
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Usuario
 * @see EstadoCuota
 */
@Entity
@Table(name = "cuotas")
public class Cuota {

    /** Identificador único de la cuota */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Jugador al que pertenece la cuota */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    @JsonIgnoreProperties("cuotas")
    @NotNull(message = "Debe indicar el jugador asociado a la cuota")
    private Usuario jugador;

    /** Concepto o descripción de la cuota (ej: "Mensualidad Enero", "Equipación") */
    private String concepto;

    /** Importe de la cuota en euros */
    @Column(nullable = false)
    @NotNull(message = "Debe indicar el importe de la cuota")
    private Double importe=0.0;

    /** Estado actual de la cuota (PENDIENTE, PAGADO, EXENTO) */
    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCuota estadoCuota;

    /** Fecha en la que se realizó el pago (null si está pendiente o exento) */
    private LocalDate fechaPago;

    /**
     * Constructor por defecto.
     */
    public Cuota() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param jugador Jugador al que pertenece la cuota
     * @param concepto Concepto o descripción de la cuota
     * @param importe Importe de la cuota en euros
     * @param estadoCuota Estado de la cuota
     * @param fechaPago Fecha de pago (puede ser null)
     */
    public Cuota(Usuario jugador, String concepto, Double importe, EstadoCuota estadoCuota, LocalDate fechaPago) {
        this.jugador = jugador;
        this.concepto = concepto;
        this.importe = importe;
        this.estadoCuota = estadoCuota;
        this.fechaPago = fechaPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getJugador() {
        return jugador;
    }

    public void setJugador(Usuario jugador) {
        this.jugador = jugador;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public EstadoCuota getEstadoCuota() {
        return estadoCuota;
    }

    public void setEstadoCuota(EstadoCuota estadoCuota) {
        this.estadoCuota = estadoCuota;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    @Override
    public String toString() {
        return "Cuota{id=" + id +
                ", jugador=" + (jugador != null ? jugador.getNombre() : "N/A") +
                ", concepto='" + concepto + '\'' +
                ", importe=" + importe +
                ", estado=" + estadoCuota +
                ", fechaPago=" + fechaPago + "}";
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
        Cuota other = (Cuota) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
