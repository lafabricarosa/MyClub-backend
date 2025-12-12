package com.gestiondeportiva.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Cuota;
import com.gestiondeportiva.api.entities.EstadoCuota;

/**
 * Repositorio JPA para la gestión de entidades Cuota.
 * <p>
 * Gestiona las cuotas de pago de los jugadores, permitiendo consultar
 * por jugador y por estado de pago.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Cuota
 * @see JpaRepository
 */
public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    /**
     * Busca todas las cuotas de un jugador específico.
     *
     * @param jugadorId ID del jugador
     * @return lista de cuotas del jugador
     */
    List<Cuota> findByJugadorId(Long jugadorId);

    /**
     * Busca todas las cuotas por estado de pago.
     * <p>
     * Útil para obtener todas las cuotas pendientes, pagadas o exentas.
     * </p>
     *
     * @param estadoCuota estado de la cuota (PENDIENTE, PAGADO, EXENTO)
     * @return lista de cuotas con el estado especificado
     */
    List<Cuota> findByEstadoCuota(EstadoCuota estadoCuota);

}
