package com.gestiondeportiva.api.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.gestiondeportiva.api.dto.ConvocatoriaDTO;
import com.gestiondeportiva.api.services.ConvocatoriaService;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de convocatorias de jugadores a eventos.
 * <p>
 * Las convocatorias vinculan jugadores con eventos específicos (partidos o entrenamientos),
 * permitiendo a los entrenadores gestionar qué jugadores participan en cada actividad.
 * Cada convocatoria puede incluir información sobre la asistencia del jugador.
 * </p>
 *
 * <p><strong>Endpoints:</strong></p>
 * <ul>
 *   <li>GET /api/convocatorias - Lista todas las convocatorias</li>
 *   <li>GET /api/convocatorias/{id} - Obtiene una convocatoria por ID</li>
 *   <li>POST /api/convocatorias - Crea una nueva convocatoria</li>
 *   <li>PUT /api/convocatorias/{id} - Actualiza una convocatoria</li>
 *   <li>DELETE /api/convocatorias/{id} - Elimina una convocatoria</li>
 * </ul>
 *
 * <p><strong>Modelo de datos:</strong></p>
 * <ul>
 *   <li>Evento: A qué partido o entrenamiento pertenece la convocatoria</li>
 *   <li>Jugador: Qué jugador es convocado</li>
 *   <li>Asistió: Indicador de si el jugador asistió o no (opcional)</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see ConvocatoriaService
 */
@RestController
@RequestMapping("/api/convocatorias")
public class ConvocatoriaController {

    private final ConvocatoriaService convocatoriaService;

    public ConvocatoriaController(ConvocatoriaService convocatoriaService){
        this.convocatoriaService = convocatoriaService;
    }

    /**
     * Lista todas las convocatorias del sistema.
     *
     * @return ResponseEntity con lista de ConvocatoriaDTO y código HTTP 200
     */
    @GetMapping
    public ResponseEntity <List<ConvocatoriaDTO>> findAll(){
        return ResponseEntity.ok(convocatoriaService.findAll());
    }

    /**
     * Obtiene una convocatoria por su ID.
     *
     * @param id ID de la convocatoria a buscar
     * @return ResponseEntity con ConvocatoriaDTO y código 200 si existe, 404 si no
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConvocatoriaDTO> findById(@PathVariable Long id){
        return convocatoriaService.findById(id)
            .map(c -> ResponseEntity.ok(c))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva convocatoria de jugador a un evento.
     * <p>
     * Vincula un jugador específico con un evento (partido o entrenamiento).
     * Típicamente utilizado por entrenadores para crear listas de jugadores
     * convocados para cada actividad.
     * </p>
     *
     * @param dto datos de la nueva convocatoria con validaciones aplicadas
     * @return ResponseEntity con ConvocatoriaDTO creada y código HTTP 201
     */
    @PostMapping
    public ResponseEntity<ConvocatoriaDTO> create(@Valid @RequestBody ConvocatoriaDTO dto){
        ConvocatoriaDTO guardada = convocatoriaService.save(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardada);
    }

    /**
     * Actualiza una convocatoria existente.
     * <p>
     * Permite modificar datos como el estado de asistencia del jugador al evento.
     * </p>
     *
     * @param id ID de la convocatoria a actualizar
     * @param dto nuevos datos de la convocatoria con validaciones aplicadas
     * @return ResponseEntity con ConvocatoriaDTO actualizada y código HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConvocatoriaDTO> update(@PathVariable Long id, @Valid @RequestBody ConvocatoriaDTO dto){
        ConvocatoriaDTO actualizada = convocatoriaService.update(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Elimina una convocatoria por su ID.
     *
     * @param id ID de la convocatoria a eliminar
     * @return ResponseEntity vacío con código HTTP 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        convocatoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
