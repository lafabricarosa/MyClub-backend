package com.gestiondeportiva.api.dto;

public class EstadisticaDTO {

    private Long id;
    private Long idJugador;
    private String nombreJugador;
    private Long idEvento;
    private String descripcionEvento;
    private Integer goles;
    private Integer tarjetasAmarillas;
    private Integer tarjetasRojas;

    public EstadisticaDTO() {
    }

    public EstadisticaDTO(Long id, Long idJugador, String nombreJugador,
                          Long idEvento, String descripcionEvento,
                          Integer goles, Integer tarjetasAmarillas, 
                          Integer tarjetasRojas) {
        this.id = id;
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.idEvento = idEvento;
        this.descripcionEvento = descripcionEvento;
        this.goles = goles;
        this.tarjetasAmarillas = tarjetasAmarillas;
        this.tarjetasRojas = tarjetasRojas;
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

}

