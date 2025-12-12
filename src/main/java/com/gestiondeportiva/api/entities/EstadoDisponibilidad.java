package com.gestiondeportiva.api.entities;

/**
 * Enumeración que define el estado de disponibilidad de un jugador para un evento.
 * <p>
 * Permite a los jugadores comunicar al entrenador si podrán asistir a entrenamientos,
 * partidos o reuniones del equipo.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Disponibilidad
 */
public enum EstadoDisponibilidad {
    /** El jugador confirma que asistirá al evento */
    ASISTE,

    /** El jugador no podrá asistir al evento */
    NO_ASISTE,

    /** El jugador aún no está seguro de su asistencia */
    DUDA
}
