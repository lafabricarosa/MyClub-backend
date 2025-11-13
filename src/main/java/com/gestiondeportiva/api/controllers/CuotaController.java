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


@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    private final CuotaService cuotaService;

    public CuotaController(CuotaService cuotaService){
        this.cuotaService = cuotaService;
    }

    @GetMapping
    public ResponseEntity<List<CuotaDTO>> findAll(){
        return ResponseEntity.ok(cuotaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuotaDTO> findById(@PathVariable Long id){
        return cuotaService.findById(id)
            .map(c -> ResponseEntity.ok(c))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CuotaDTO> create(@Valid @RequestBody CuotaDTO cuotaDTO){
        CuotaDTO guardada = cuotaService.save(cuotaDTO);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuotaDTO> update(@PathVariable Long id, @Valid @RequestBody CuotaDTO dto){
        CuotaDTO actualizada = cuotaService.update(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cuotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
