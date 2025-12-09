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

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<EquipoDTO>> findAll() {
        return ResponseEntity.ok(equipoService.findAll());
    }

    // Obtener equipo por id
    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> findById(@PathVariable Long id) {
        return equipoService.findById(id)
            .map(e -> ResponseEntity.ok(e))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un equipo
    @PreAuthorize("hasRole('ENTRENADOR')")
    @PostMapping
    public ResponseEntity<EquipoDTO> create(@Valid @RequestBody EquipoDTO dto) {
        EquipoDTO guardado = equipoService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(guardado);
    }

    // Actualizar un equipo
    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> update(@PathVariable Long id, @Valid @RequestBody EquipoDTO dto) {
        EquipoDTO actualizado = equipoService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Borrar un equipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        equipoService.deleteById(id);
        return ResponseEntity.noContent().build();
        
    }

}
