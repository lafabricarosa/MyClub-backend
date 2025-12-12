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

/**
 * Controlador REST para la gestión de usuarios del sistema.
 * <p>
 * Expone endpoints para operaciones CRUD de usuarios, incluyendo funcionalidades
 * especiales como subida de fotos de perfil y cambio de contraseña. Implementa
 * control de acceso basado en roles mediante @PreAuthorize.
 * </p>
 *
 * <p><strong>Endpoints principales:</strong></p>
 * <ul>
 *   <li>GET /api/usuarios - Lista todos los usuarios (ADMIN, ENTRENADOR)</li>
 *   <li>GET /api/usuarios/{id} - Obtiene un usuario por ID</li>
 *   <li>GET /api/usuarios/me - Obtiene datos del usuario autenticado</li>
 *   <li>POST /api/usuarios - Crea un nuevo usuario (ADMIN, ENTRENADOR)</li>
 *   <li>PUT /api/usuarios/{id} - Actualiza un usuario</li>
 *   <li>DELETE /api/usuarios/{id} - Elimina un usuario (ADMIN, ENTRENADOR)</li>
 *   <li>POST /api/usuarios/{id}/foto - Sube foto de perfil a Cloudinary</li>
 *   <li>PUT /api/usuarios/{id}/cambiar-password - Cambia la contraseña</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see UsuarioService
 * @see CloudinaryService
 */
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

    /**
     * Lista todos los usuarios del sistema.
     * <p>
     * El servicio aplica filtros según el rol:
     * ADMIN ve todos, ENTRENADOR solo ve jugadores de su equipo.
     * </p>
     *
     * @return ResponseEntity con lista de UsuarioDTO y código HTTP 200
     */
    @PreAuthorize("hasAnyRole('ENTRENADOR','ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    /**
     * Obtiene un usuario por su ID.
     * <p>
     * El servicio verifica permisos de acceso según el rol del usuario autenticado.
     * </p>
     *
     * @param id ID del usuario a buscar
     * @return ResponseEntity con UsuarioDTO y código 200 si existe, 404 si no
     */
    @PreAuthorize("hasAnyRole('JUGADOR','ENTRENADOR', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * <p>
     * La contraseña se encripta automáticamente con BCrypt.
     * ENTRENADOR solo puede crear JUGADORES de su equipo.
     * </p>
     *
     * @param dto datos del nuevo usuario con validaciones aplicadas
     * @return ResponseEntity con UsuarioDTO creado y código HTTP 201
     */
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

    /**
     * Obtiene los datos del usuario autenticado actualmente.
     * <p>
     * Extrae el email del token JWT y busca los datos completos del usuario.
     * Útil para que el frontend obtenga información del usuario logueado.
     * </p>
     *
     * @return ResponseEntity con UsuarioDTO del usuario autenticado y código 200
     */
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

    /**
     * Sube o actualiza la foto de perfil de un usuario en Cloudinary.
     * <p>
     * Si el usuario ya tiene una foto, se elimina de Cloudinary antes de subir la nueva.
     * La imagen se almacena en la carpeta 'myclub/fotos-perfil' y se optimiza automáticamente.
     * </p>
     *
     * @param id ID del usuario
     * @param file archivo de imagen en formato MultipartFile
     * @return ResponseEntity con UsuarioDTO actualizado incluyendo la nueva URL de foto
     * @throws EntityNotFoundException si el usuario no existe
     */
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

    /**
     * Cambia la contraseña de un usuario.
     * <p>
     * Verifica la contraseña actual antes de establecer la nueva.
     * La nueva contraseña se encripta con BCrypt automáticamente.
     * </p>
     *
     * @param id ID del usuario
     * @param request objeto con passwordActual y passwordNueva
     * @return ResponseEntity con código 200 si el cambio fue exitoso
     * @throws IllegalArgumentException si la contraseña actual es incorrecta
     */
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
