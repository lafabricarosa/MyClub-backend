package com.gestiondeportiva.api.dto;

/**
 * DTO (Data Transfer Object) para transferir estadísticas de jugadores en respuestas API.
 * <p>
 * Contiene las estadísticas de rendimiento de un jugador en un evento específico,
 * incluyendo goles marcados y tarjetas recibidas. Incluye información desnormalizada
 * del jugador y evento para facilitar la visualización.
 * </p>
 *
 * <p><strong>Nota:</strong> El sistema implementa lógica de upsert para evitar duplicados.
 * Si ya existe una estadística para el mismo jugador y evento, se actualiza en lugar
 * de crear un nuevo registro.</p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.entities.Estadistica
 */
public class EstadisticaDTO {

    /** Identificador único de la estadística */
    private Long id;

    /** ID del jugador */
    private Long idJugador;

    /** Nombre del jugador (desnormalizado) */
    private String nombreJugador;

    /** ID del evento */
    private Long idEvento;

    /** Descripción del evento (desnormalizado) */
    private String descripcionEvento;

    /** Número de goles marcados por el jugador */
    private Integer goles;

    /** Número de tarjetas amarillas recibidas */
    private Integer tarjetasAmarillas;

    /** Número de tarjetas rojas recibidas */
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

