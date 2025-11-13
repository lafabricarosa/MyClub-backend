package com.gestiondeportiva.api.dto;

public class ConvocatoriaDTO {

    private Long id;
    private Long idEvento;
    private String descripcionEvento;
    private Long idJugador;
    private String nombreJugador;
    private Boolean titular;

    public ConvocatoriaDTO() {
    }

    public ConvocatoriaDTO(Long id, Long idEvento, String descripcionEvento,
                           Long idJugador, String nombreJugador, Boolean titular) {
        this.id = id;
        this.idEvento = idEvento;
        this.descripcionEvento = descripcionEvento;
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.titular = titular;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
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

    public Boolean getTitular() {
        return titular;
    }

    public void setTitular(Boolean titular) {
        this.titular = titular;
    }
}