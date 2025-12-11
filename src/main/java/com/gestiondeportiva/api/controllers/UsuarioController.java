package com.gestiondeportiva.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gestiondeportiva.api.dto.UsuarioCreateDTO;
import com.gestiondeportiva.api.dto.UsuarioDTO;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.mappers.UsuarioMapper;
import com.gestiondeportiva.api.repositories.UsuarioRepository;
import com.gestiondeportiva.api.services.CloudinaryService;
import com.gestiondeportiva.api.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final CloudinaryService cloudinaryService;

    public UsuarioController(UsuarioService usuarioService,
                            UsuarioRepository usuarioRepository,
                            UsuarioMapper usuarioMapper,
                            CloudinaryService cloudinaryService) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @PreAuthorize("hasAnyRole('ENTRENADOR','ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PreAuthorize("hasAnyRole('JUGADOR','ENTRENADOR', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENTRENADOR')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO dto) {
        UsuarioDTO guardado = usuarioService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO actualizado = usuarioService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENTRENADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // Buscar el usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Convertir a DTO
        UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuario);

        return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping("/{id}/foto")
    public ResponseEntity<UsuarioDTO> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        // Buscar el usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Eliminar la foto anterior de Cloudinary si existe
        if (usuario.getFotoUrl() != null && !usuario.getFotoUrl().isEmpty()) {
            cloudinaryService.eliminarImagen(usuario.getFotoUrl());
        }

        // Subir la nueva imagen a Cloudinary
        String imageUrl = cloudinaryService.subirImagen(file, "myclub/fotos-perfil");

        // Actualizar la URL de la foto en el usuario
        usuario.setFotoUrl(imageUrl);
        Usuario actualizado = usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuarioMapper.toDTO(actualizado));
    }

    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody @Valid ChangePasswordRequest request) {

        usuarioService.cambiarPassword(id, request.getPasswordActual(), request.getPasswordNueva());
        return ResponseEntity.ok().build();
    }

    // Clase interna para el request de cambio de contraseña
    public static class ChangePasswordRequest {
        private String passwordActual;
        private String passwordNueva;

        public String getPasswordActual() {
            return passwordActual;
        }

        public void setPasswordActual(String passwordActual) {
            this.passwordActual = passwordActual;
        }

        public String getPasswordNueva() {
            return passwordNueva;
        }

        public void setPasswordNueva(String passwordNueva) {
            this.passwordNueva = passwordNueva;
        }
    }

}
