package com.gestiondeportiva.api.dto;

import java.time.LocalDate;

import com.gestiondeportiva.api.entities.EstadoCuota;

/**
 * DTO (Data Transfer Object) para transferir datos de cuotas en respuestas API.
 * <p>
 * Representa una cuota de pago de un jugador, incluyendo el concepto, importe,
 * estado de pago y fecha de pago. Incluye el nombre del jugador desnormalizado
 * para facilitar la visualización en el frontend.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.entities.Cuota
 */
public class CuotaDTO {

    /** Identificador único de la cuota */
    private Long id;

    /** ID del jugador al que pertenece la cuota */
    private Long idJugador;

    /** Nombre del jugador (desnormalizado) */
    private String nombreJugador;

    /** Concepto o descripción de la cuota */
    private String concepto;

    /** Importe de la cuota en euros */
    private Double importe;

    /** Estado de la cuota (PENDIENTE, PAGADO, EXENTO) */
    private EstadoCuota estadoCuota;

    /** Fecha en la que se realizó el pago (null si está pendiente) */
    private LocalDate fechaPago;

    public CuotaDTO() {
    }

    public CuotaDTO(Long id, Long idJugador, String nombreJugador,
                    String concepto, Double importe,
                    EstadoCuota estadoCuota, LocalDate fechaPago) {
        this.id = id;
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.concepto = concepto;
        this.importe = importe;
        this.estadoCuota = estadoCuota;
        this.fechaPago = fechaPago;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Long idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
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
}
