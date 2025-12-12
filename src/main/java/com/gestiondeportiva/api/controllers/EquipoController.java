package com.gestiondeportiva.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestiondeportiva.api.dto.EquipoDTO;
import com.gestiondeportiva.api.services.EquipoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador REST para la gestión de equipos deportivos.
 * <p>
 * Proporciona endpoints CRUD para la administración de equipos dentro del sistema.
 * Los equipos son la entidad principal que agrupa a jugadores y entrenadores en la
 * plataforma MyClub.
 * </p>
 *
 * <p><strong>Endpoints:</strong></p>
 * <ul>
 *   <li>GET /api/equipos - Lista todos los equipos</li>
 *   <li>GET /api/equipos/{id} - Obtiene un equipo por ID</li>
 *   <li>POST /api/equipos - Crea un nuevo equipo (solo ENTRENADOR)</li>
 *   <li>PUT /api/equipos/{id} - Actualiza un equipo</li>
 *   <li>DELETE /api/equipos/{id} - Elimina un equipo</li>
 * </ul>
 *
 * <p><strong>Control de acceso:</strong></p>
 * <ul>
 *   <li>Lectura (GET): Acceso público o por usuarios autenticados</li>
 *   <li>Creación (POST): Solo ENTRENADOR</li>
 *   <li>Actualización/Eliminación: Depende de las reglas de negocio del servicio</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see EquipoService
 */
@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    /**
     * Lista todos los equipos del sistema.
     *
     * @return ResponseEntity con lista de EquipoDTO y código HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<EquipoDTO>> findAll() {
        return ResponseEntity.ok(equipoService.findAll());
    }

    /**
     * Obtiene un equipo por su ID.
     *
     * @param id ID del equipo a buscar
     * @return ResponseEntity con EquipoDTO y código 200 si existe, 404 si no
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> findById(@PathVariable Long id) {
        return equipoService.findById(id)
            .map(e -> ResponseEntity.ok(e))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo equipo en el sistema.
     * <p>
     * Solo los usuarios con rol ENTRENADOR pueden crear equipos.
     * </p>
     *
     * @param dto datos del nuevo equipo con validaciones aplicadas
     * @return ResponseEntity con EquipoDTO creado y código HTTP 201
     */
    @PreAuthorize("hasRole('ENTRENADOR')")
    @PostMapping
    public ResponseEntity<EquipoDTO> create(@Valid @RequestBody EquipoDTO dto) {
        EquipoDTO guardado = equipoService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(guardado);
    }

    /**
     * Actualiza un equipo existente.
     *
     * @param id ID del equipo a actualizar
     * @param dto nuevos datos del equipo con validaciones aplicadas
     * @return ResponseEntity con EquipoDTO actualizado y código HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> update(@PathVariable Long id, @Valid @RequestBody EquipoDTO dto) {
        EquipoDTO actualizado = equipoService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un equipo por su ID.
     *
     * @param id ID del equipo a eliminar
     * @return ResponseEntity vacío con código HTTP 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        equipoService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}
