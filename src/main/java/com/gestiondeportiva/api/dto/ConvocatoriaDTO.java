package com.gestiondeportiva.api.dto;

/**
 * DTO (Data Transfer Object) para transferir datos de convocatorias en respuestas API.
 * <p>
 * Representa la convocatoria de un jugador para un evento específico, indicando
 * si es titular o suplente. Incluye información desnormalizada del evento y jugador.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.entities.Convocatoria
 */
public class ConvocatoriaDTO {

    /** Identificador único de la convocatoria */
    private Long id;

    /** ID del evento */
    private Long idEvento;

    /** Descripción del evento (desnormalizado) */
    private String descripcionEvento;

    /** ID del jugador convocado */
    private Long idJugador;

    /** Nombre del jugador (desnormalizado) */
    private String nombreJugador;

    /** Indica si el jugador es titular (true) o suplente (false) */
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