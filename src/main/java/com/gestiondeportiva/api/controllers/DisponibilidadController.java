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

import com.gestiondeportiva.api.dto.DisponibilidadDTO;
import com.gestiondeportiva.api.services.DisponibilidadService;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de disponibilidades de jugadores.
 * <p>
 * Las disponibilidades permiten a los jugadores indicar su disponibilidad para asistir
 * a eventos específicos (partidos o entrenamientos). Esto facilita a los entrenadores
 * la planificación de convocatorias sabiendo de antemano qué jugadores pueden participar.
 * </p>
 *
 * <p><strong>Endpoints:</strong></p>
 * <ul>
 *   <li>GET /api/disponibilidades - Lista todas las disponibilidades</li>
 *   <li>GET /api/disponibilidades/{id} - Obtiene una disponibilidad por ID</li>
 *   <li>POST /api/disponibilidades - Crea una nueva disponibilidad</li>
 *   <li>PUT /api/disponibilidades/{id} - Actualiza una disponibilidad</li>
 *   <li>DELETE /api/disponibilidades/{id} - Elimina una disponibilidad</li>
 * </ul>
 *
 * <p><strong>Modelo de datos:</strong></p>
 * <ul>
 *   <li>Jugador: Quién declara su disponibilidad</li>
 *   <li>Evento: Para qué partido o entrenamiento</li>
 *   <li>Estado: Disponible, No disponible, Dudoso, etc.</li>
 *   <li>Motivo: Razón opcional de la indisponibilidad (lesión, trabajo, etc.)</li>
 * </ul>
 *
 * <p><strong>Flujo típico de uso:</strong></p>
 * <ol>
 *   <li>El entrenador crea un evento (partido o entrenamiento)</li>
 *   <li>Los jugadores consultan los próximos eventos</li>
 *   <li>Cada jugador indica su disponibilidad para cada evento</li>
 *   <li>El entrenador consulta las disponibilidades antes de crear convocatorias</li>
 * </ol>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see DisponibilidadService
 */
@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    public DisponibilidadController (DisponibilidadService disponibilidadService){
        this.disponibilidadService = disponibilidadService;
    }

    /**
     * Lista todas las disponibilidades del sistema.
     *
     * @return ResponseEntity con lista de DisponibilidadDTO y código HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<DisponibilidadDTO>> findAll(){
        return ResponseEntity.ok(disponibilidadService.findAll());
    }

    /**
     * Obtiene una disponibilidad por su ID.
     *
     * @param id ID de la disponibilidad a buscar
     * @return ResponseEntity con DisponibilidadDTO y código 200 si existe, 404 si no
     */
    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadDTO> findById(@PathVariable Long id){
        return disponibilidadService.findById(id)
            .map(d -> ResponseEntity.ok(d))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva disponibilidad de un jugador para un evento.
     * <p>
     * Permite a un jugador indicar si estará disponible para asistir a un evento específico.
     * Esto ayuda a los entrenadores a planificar convocatorias de manera más eficiente.
     * </p>
     *
     * @param dto datos de la nueva disponibilidad con validaciones aplicadas
     * @return ResponseEntity con DisponibilidadDTO creada y código HTTP 201
     */
    @PostMapping
    public ResponseEntity<DisponibilidadDTO> create(@Valid @RequestBody DisponibilidadDTO dto){
        DisponibilidadDTO guardado = disponibilidadService.save(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardado);
    }

    /**
     * Actualiza una disponibilidad existente.
     * <p>
     * Permite a un jugador cambiar su disponibilidad declarada (por ejemplo, de "Disponible"
     * a "No disponible" si surge un imprevisto) o actualizar el motivo de su indisponibilidad.
     * </p>
     *
     * @param id ID de la disponibilidad a actualizar
     * @param dto nuevos datos de la disponibilidad con validaciones aplicadas
     * @return ResponseEntity con DisponibilidadDTO actualizada y código HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadDTO> update(@PathVariable Long id, @Valid @RequestBody DisponibilidadDTO dto){
        DisponibilidadDTO actualizado = disponibilidadService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina una disponibilidad por su ID.
     *
     * @param id ID de la disponibilidad a eliminar
     * @return ResponseEntity vacío con código HTTP 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        disponibilidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
