package com.gestiondeportiva.api.controllers;

import java.util.List;

import com.gestiondeportiva.api.services.EstadisticaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestiondeportiva.api.dto.EstadisticaDTO;

/**
 * Controlador REST para la gestión de estadísticas de jugadores.
 * <p>
 * Maneja operaciones CRUD para estadísticas de rendimiento de jugadores en eventos
 * (goles, tarjetas amarillas y rojas). El servicio implementa lógica de upsert
 * para prevenir duplicados de estadísticas del mismo jugador en el mismo evento.
 * </p>
 *
 * <p><strong>Endpoints:</strong></p>
 * <ul>
 *   <li>GET /api/estadisticas - Lista todas las estadísticas</li>
 *   <li>GET /api/estadisticas/{id} - Obtiene una estadística por ID</li>
 *   <li>POST /api/estadisticas - Crea/actualiza estadística (upsert)</li>
 *   <li>PUT /api/estadisticas/{id} - Actualiza una estadística</li>
 *   <li>DELETE /api/estadisticas/{id} - Elimina una estadística</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see EstadisticaService
 */
@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaController {

    private final EstadisticaService estadisticaService;

    public EstadisticaController(EstadisticaService estadisticaService){
        this.estadisticaService = estadisticaService;
    }

    /**
     * Lista todas las estadísticas del sistema.
     *
     * @return ResponseEntity con lista de EstadisticaDTO y código 200
     */
    @GetMapping
    public ResponseEntity<List<EstadisticaDTO>> findAll(){
        return ResponseEntity.ok(estadisticaService.findAll());
    }

    /**
     * Obtiene una estadística por su ID.
     *
     * @param id ID de la estadística
     * @return ResponseEntity con EstadisticaDTO y código 200 si existe, 404 si no
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstadisticaDTO> findById(@PathVariable Long id){
        return estadisticaService.findById(id)
            .map(estadistica -> ResponseEntity.ok(estadistica))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea o actualiza una estadística con lógica de upsert.
     * <p>
     * Si ya existe una estadística para ese jugador en ese evento, la actualiza.
     * Si no existe, crea una nueva. Esto previene duplicados.
     * </p>
     *
     * @param dto datos de la estadística con validaciones
     * @return ResponseEntity con EstadisticaDTO creada/actualizada y código 201
     */
    @PostMapping
    public ResponseEntity<EstadisticaDTO> create(@Valid @RequestBody EstadisticaDTO dto){
        EstadisticaDTO guardada = estadisticaService.save(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardada);
    }

    /**
     * Actualiza una estadística existente.
     *
     * @param id ID de la estadística
     * @param estadisticaDTO nuevos datos de la estadística
     * @return ResponseEntity con EstadisticaDTO actualizada y código 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<EstadisticaDTO> update(@PathVariable Long id, @Valid @RequestBody EstadisticaDTO estadisticaDTO){
        EstadisticaDTO actualizada = estadisticaService.update(id, estadisticaDTO);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Elimina una estadística por su ID.
     *
     * @param id ID de la estadística a eliminar
     * @return ResponseEntity vacío con código 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        estadisticaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
