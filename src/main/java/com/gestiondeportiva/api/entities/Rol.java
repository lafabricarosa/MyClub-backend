package com.gestiondeportiva.api.entities;

/**
 * Enumeración que define los roles de usuario en el sistema de gestión deportiva.
 * <p>
 * Los roles determinan los permisos y funcionalidades disponibles para cada usuario:
 * <ul>
 *   <li><b>ADMIN:</b> Acceso completo al sistema. Puede gestionar todos los usuarios, equipos y eventos.</li>
 *   <li><b>ENTRENADOR:</b> Gestiona un equipo específico. Puede crear jugadores de su equipo,
 *       gestionar eventos, convocatorias y estadísticas.</li>
 *   <li><b>JUGADOR:</b> Acceso limitado a sus propios datos, eventos de su equipo y registro
 *       de disponibilidad.</li>
 * </ul>
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Usuario
 */
public enum Rol {
    /** Rol de entrenador que dirige un equipo */
    ENTRENADOR,

    /** Rol de jugador miembro de un equipo */
    JUGADOR,

    /** Rol de administrador con acceso completo al sistema */
    ADMIN
}
