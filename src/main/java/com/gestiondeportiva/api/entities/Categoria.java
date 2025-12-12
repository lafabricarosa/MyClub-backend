package com.gestiondeportiva.api.entities;

/**
 * Enumeración que define las categorías de edad de los equipos deportivos.
 * <p>
 * Las categorías siguen la clasificación estándar del fútbol base español,
 * desde la categoría senior (adultos) hasta prebenjamín (más jóvenes).
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Equipo
 */
public enum Categoria {
    /** Categoría adulta (mayores de 18 años) */
    SENIOR,

    /** Sub-18 años */
    JUVENIL,

    /** Sub-16 años */
    CADETE,

    /** Sub-14 años */
    INFANTIL,

    /** Sub-12 años */
    ALEVIN,

    /** Sub-10 años */
    BENJAMIN,

    /** Sub-8 años */
    PREBENJAMIN
}
