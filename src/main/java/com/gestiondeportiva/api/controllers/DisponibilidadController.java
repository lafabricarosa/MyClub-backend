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

@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    public DisponibilidadController (DisponibilidadService disponibilidadService){
        this.disponibilidadService = disponibilidadService;
    }

    @GetMapping
    public ResponseEntity<List<DisponibilidadDTO>> findAll(){
        return ResponseEntity.ok(disponibilidadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadDTO> findById(@PathVariable Long id){
        return disponibilidadService.findById(id)
            .map(d -> ResponseEntity.ok(d))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DisponibilidadDTO> create(@Valid @RequestBody DisponibilidadDTO dto){
        DisponibilidadDTO guardado = disponibilidadService.save(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadDTO> update(@PathVariable Long id, @Valid @RequestBody DisponibilidadDTO dto){
        DisponibilidadDTO actualizado = disponibilidadService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        disponibilidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
