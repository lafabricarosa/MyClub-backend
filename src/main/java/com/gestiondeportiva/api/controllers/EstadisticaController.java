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

// Indica que esta clase es un controlador REST, manejará solicitudes HTTP (GET, POST, PUT, DELETE, etc.)
@RestController
// Define la ruta base para todas las peticiones que manejará este controlador.
@RequestMapping("/api/estadisticas")
public class EstadisticaController {

    // Servicio que contiene la lógica de negocio relacionada con las estadísticas
    private final EstadisticaService estadisticaService;

    // Constructor que inyecta el servicio (inyección de dependencias)
    public EstadisticaController(EstadisticaService estadisticaService){
        this.estadisticaService = estadisticaService;
    }

    // Devuelve una lista con todas las estadísticas registradas
    @GetMapping 
    public ResponseEntity<List<EstadisticaDTO>> findAll(){
        // ResponseEntity.ok() devuelve una respuesta HTTP 200 con el cuerpo que se pasa
        return ResponseEntity.ok(estadisticaService.findAll());
    }

    // Busca una estadística específica por su ID
    @GetMapping("/{id}") 
    public ResponseEntity<EstadisticaDTO> findById(@PathVariable Long id){
        // Usa Optional para manejar el caso en que no se encuentre la estadística
        return estadisticaService.findById(id)
            .map(estadistica -> ResponseEntity.ok(estadistica))  // Si existe, devuelve 200 OK con el dato
            .orElseGet(() -> ResponseEntity.notFound().build()); // Si no existe, devuelve 404 Not Found
    }

    // Crea una nueva estadística en la base de datos
    @PostMapping 
    public ResponseEntity<EstadisticaDTO> create(@Valid @RequestBody EstadisticaDTO dto){
        // Guarda la nueva estadística y la devuelve
        EstadisticaDTO guardada = estadisticaService.save(dto);
        // Devuelve un código 201 Created y el objeto creado en el cuerpo de la respuesta
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(guardada);
    }

    // Actualiza una estadística existente
    @PutMapping("/{id}") 
    public ResponseEntity<EstadisticaDTO> update(@PathVariable Long id, @Valid @RequestBody EstadisticaDTO estadisticaDTO){
        // Llama al servicio para actualizar la entidad con los nuevos datos
        EstadisticaDTO actualizada = estadisticaService.update(id, estadisticaDTO); 
        // Devuelve 200 OK con la estadística actualizada
        return ResponseEntity.ok(actualizada);
    }

    // Elimina una estadística por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        // Llama al servicio para eliminar la estadística
        estadisticaService.deleteById(id);
        // Devuelve 204 No Content 
        return ResponseEntity.noContent().build();
    }
}
