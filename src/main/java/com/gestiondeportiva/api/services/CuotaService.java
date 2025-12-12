package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.CuotaDTO;
import com.gestiondeportiva.api.entities.EstadoCuota;

/**
 * Servicio de lógica de negocio para la gestión de cuotas de pago.
 * <p>
 * Proporciona operaciones CRUD y consultas para la gestión económica
 * de cuotas de los jugadores.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see CuotaDTO
 */
public interface CuotaService {

    /**
     * Obtiene todas las cuotas del sistema.
     *
     * @return lista de todas las cuotas
     */
    List<CuotaDTO> findAll();

    /**
     * Crea una nueva cuota.
     *
     * @param cuotadto datos de la nueva cuota
     * @return CuotaDTO de la cuota creada
     */
    CuotaDTO save(CuotaDTO cuotadto);

    /**
     * Actualiza una cuota existente.
     *
     * @param id ID de la cuota a actualizar
     * @param cuotaDTO datos a actualizar
     * @return CuotaDTO con los datos actualizados
     */
    CuotaDTO update(Long id, CuotaDTO cuotaDTO);

    /**
     * Elimina una cuota por su ID.
     *
     * @param id ID de la cuota a eliminar
     */
    void deleteById(Long id);

    /**
     * Busca una cuota por su ID.
     *
     * @param id ID de la cuota
     * @return Optional con la cuota si existe, Optional.empty() si no
     */
    Optional<CuotaDTO> findById(Long id);

    /**
     * Busca todas las cuotas de un jugador.
     *
     * @param jugadorId ID del jugador
     * @return lista de cuotas del jugador
     */
    List<CuotaDTO> findByJugadorId(Long jugadorId);

    /**
     * Busca cuotas por estado de pago.
     * <p>
     * Útil para obtener todas las cuotas pendientes, pagadas o exentas.
     * </p>
     *
     * @param estadoCuota estado de la cuota (PENDIENTE, PAGADO, EXENTO)
     * @return lista de cuotas con el estado especificado
     */
    List<CuotaDTO> findByEstadoCuota(EstadoCuota estadoCuota);

}
