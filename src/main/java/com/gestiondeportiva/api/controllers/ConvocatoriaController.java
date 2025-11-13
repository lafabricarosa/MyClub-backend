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

@RestController
@RequestMapping("/api/convocatorias")
public class ConvocatoriaController {

    private final ConvocatoriaService convocatoriaService;

    public ConvocatoriaController(ConvocatoriaService convocatoriaService){
        this.convocatoriaService = convocatoriaService;
    }

    @GetMapping
    public ResponseEntity <List<ConvocatoriaDTO>> findAll(){
        return ResponseEntity.ok(convocatoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConvocatoriaDTO> findById(@PathVariable Long id){
        return convocatoriaService.findById(id)
            .map(c -> ResponseEntity.ok(c))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConvocatoriaDTO> create(@Valid @RequestBody ConvocatoriaDTO dto){
        ConvocatoriaDTO guardada = convocatoriaService.save(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConvocatoriaDTO> update(@PathVariable Long id, @Valid @RequestBody ConvocatoriaDTO dto){
        ConvocatoriaDTO actualizada = convocatoriaService.update(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        convocatoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
