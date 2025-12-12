package com.gestiondeportiva.api.entities;

/**
 * Enumeración que define las posiciones de juego de los jugadores en el campo.
 * <p>
 * Representa las cuatro posiciones principales en el fútbol, desde la más defensiva
 * hasta la más ofensiva.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Usuario
 */
public enum Posicion {
    /** Guardameta del equipo */
    PORTERO,

    /** Jugador de la línea defensiva */
    DEFENSA,

    /** Jugador del medio campo */
    CENTROCAMPISTA,

    /** Jugador de ataque */
    DELANTERO
}
