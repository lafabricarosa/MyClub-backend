package com.gestiondeportiva.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.gestiondeportiva.api.dto.CuotaDTO;
import com.gestiondeportiva.api.services.CuotaService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador REST para la gestión de cuotas de jugadores.
 * <p>
 * Las cuotas representan los pagos periódicos (mensuales, trimestrales, etc.) que los
 * jugadores deben realizar al equipo. Este controlador permite crear, consultar y gestionar
 * el estado de pago de cada cuota asociada a un jugador.
 * </p>
 *
 * <p><strong>Endpoints:</strong></p>
 * <ul>
 *   <li>GET /api/cuotas - Lista todas las cuotas del sistema</li>
 *   <li>GET /api/cuotas/{id} - Obtiene una cuota por ID</li>
 *   <li>POST /api/cuotas - Crea una nueva cuota</li>
 *   <li>PUT /api/cuotas/{id} - Actualiza una cuota (ej: marcar como pagada)</li>
 *   <li>DELETE /api/cuotas/{id} - Elimina una cuota</li>
 * </ul>
 *
 * <p><strong>Modelo de datos:</strong></p>
 * <ul>
 *   <li>Jugador: A quién pertenece la cuota</li>
 *   <li>Monto: Importe de la cuota</li>
 *   <li>Fecha de vencimiento: Cuándo debe pagarse</li>
 *   <li>Estado de pago: Si está pagada o pendiente</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see CuotaService
 */
@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    private final CuotaService cuotaService;

    public CuotaController(CuotaService cuotaService){
        this.cuotaService = cuotaService;
    }

    /**
     * Lista todas las cuotas del sistema.
     *
     * @return ResponseEntity con lista de CuotaDTO y código HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<CuotaDTO>> findAll(){
        return ResponseEntity.ok(cuotaService.findAll());
    }

    /**
     * Obtiene una cuota por su ID.
     *
     * @param id ID de la cuota a buscar
     * @return ResponseEntity con CuotaDTO y código 200 si existe, 404 si no
     */
    @GetMapping("/{id}")
    public ResponseEntity<CuotaDTO> findById(@PathVariable Long id){
        return cuotaService.findById(id)
            .map(c -> ResponseEntity.ok(c))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva cuota para un jugador.
     * <p>
     * Típicamente utilizado por entrenadores o administradores para registrar
     * las cuotas periódicas que los jugadores deben pagar al equipo.
     * </p>
     *
     * @param cuotaDTO datos de la nueva cuota con validaciones aplicadas
     * @return ResponseEntity con CuotaDTO creada y código HTTP 201
     */
    @PostMapping
    public ResponseEntity<CuotaDTO> create(@Valid @RequestBody CuotaDTO cuotaDTO){
        CuotaDTO guardada = cuotaService.save(cuotaDTO);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardada);
    }

    /**
     * Actualiza una cuota existente.
     * <p>
     * Permite modificar datos como el estado de pago, monto o fecha de vencimiento.
     * Comúnmente usado para marcar cuotas como pagadas.
     * </p>
     *
     * @param id ID de la cuota a actualizar
     * @param dto nuevos datos de la cuota con validaciones aplicadas
     * @return ResponseEntity con CuotaDTO actualizada y código HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<CuotaDTO> update(@PathVariable Long id, @Valid @RequestBody CuotaDTO dto){
        CuotaDTO actualizada = cuotaService.update(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Elimina una cuota por su ID.
     *
     * @param id ID de la cuota a eliminar
     * @return ResponseEntity vacío con código HTTP 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cuotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
