package com.gestiondeportiva.api.entities;

/**
 * Enumeración que define el estado de pago de una cuota.
 * <p>
 * Permite llevar un seguimiento del estado de las cuotas de los jugadores,
 * facilitando la gestión económica del club.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Cuota
 */
public enum EstadoCuota {
    /** La cuota está pendiente de pago */
    PENDIENTE,

    /** La cuota ha sido abonada */
    PAGADO,

    /** El jugador está exento de pagar esta cuota */
    EXENTO
}
