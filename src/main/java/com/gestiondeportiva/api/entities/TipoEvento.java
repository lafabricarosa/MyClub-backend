package com.gestiondeportiva.api.entities;

/**
 * Enumeración que define los tipos de eventos deportivos del sistema.
 * <p>
 * Los eventos pueden ser de tres tipos principales que requieren diferentes
 * niveles de preparación y registro de datos.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Evento
 */
public enum TipoEvento {
    /** Sesión de práctica y entrenamiento del equipo */
    ENTRENAMIENTO,

    /** Encuentro competitivo contra otro equipo */
    PARTIDO,

    /** Reunión del equipo (no deportiva) */
    REUNION,
}
