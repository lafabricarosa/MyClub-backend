package com.gestiondeportiva.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestiondeportiva.api.dto.EventoDTO;
import com.gestiondeportiva.api.services.EventoService;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de eventos deportivos (partidos y entrenamientos).
 * <p>
 * Proporciona endpoints CRUD con control de acceso por roles. El servicio aplica
 * filtrado adicional para que JUGADORES y ENTRENADORES solo vean eventos de su equipo.
 * </p>
 *
 * <p><strong>Control de acceso:</strong></p>
 * <ul>
 *   <li>ADMIN: Acceso completo a todos los eventos</li>
 *   <li>ENTRENADOR: Solo eventos de su equipo, puede crear/modificar</li>
 *   <li>JUGADOR: Solo eventos de su equipo, solo lectura</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see EventoService
 */
@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController (EventoService eventoService){
        this.eventoService = eventoService;
    }

    // ========================
    //        CRUD
    // ========================

    // 🔍 Jugador, Entrenador y Admin pueden LISTAR (el servicio filtra)
    @PreAuthorize("hasAnyRole('JUGADOR','ENTRENADOR','ADMIN')")
    @GetMapping
    public ResponseEntity<List<EventoDTO>> findAll(){
        return ResponseEntity.ok(eventoService.findAll());
    }

    // 🔍 Jugador, Entrenador y Admin pueden ver un evento (el servicio filtra)
    @PreAuthorize("hasAnyRole('JUGADOR','ENTRENADOR','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> findById(@PathVariable Long id){
        return eventoService.findById(id)
            .map(e -> ResponseEntity.ok(e))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🟢 Crear → solo ENTRENADOR y ADMIN
    @PreAuthorize("hasAnyRole('ENTRENADOR','ADMIN')")
    @PostMapping
    public ResponseEntity<EventoDTO> create(@Valid @RequestBody EventoDTO dto){
        EventoDTO guardado = eventoService.save(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardado);
    }

    // 🟡 Actualizar → solo ENTRENADOR y ADMIN
    @PreAuthorize("hasAnyRole('ENTRENADOR','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> update(@PathVariable Long id, @Valid @RequestBody EventoDTO dto){
        EventoDTO actualizado = eventoService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // 🔴 Eliminar → solo ENTRENADOR y ADMIN
    @PreAuthorize("hasAnyRole('ENTRENADOR','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        eventoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
