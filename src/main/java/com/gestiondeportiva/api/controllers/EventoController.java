package com.gestiondeportiva.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "http://localhost:4200")
public class EventoController {

    private final EventoService eventoService;

    public EventoController (EventoService eventoService){
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> findAll(){
        return ResponseEntity.ok(eventoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> findById(@PathVariable Long id){
        return eventoService.findById(id)
            .map(e -> ResponseEntity.ok(e))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventoDTO> create(@Valid @RequestBody EventoDTO dto){
        EventoDTO guardado = eventoService.save(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> update(@PathVariable Long id, @Valid @RequestBody EventoDTO dto){
        EventoDTO actualizado = eventoService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        eventoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    



}
