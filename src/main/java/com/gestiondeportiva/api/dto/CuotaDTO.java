package com.gestiondeportiva.api.dto;

import java.time.LocalDate;

import com.gestiondeportiva.api.entities.EstadoCuota;

public class CuotaDTO {

    private Long id;
    private Long idJugador;
    private String nombreJugador;
    private String concepto;
    private Double importe;
    private EstadoCuota estadoCuota;
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
