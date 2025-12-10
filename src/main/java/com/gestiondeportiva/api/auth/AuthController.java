package com.gestiondeportiva.api.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.gestiondeportiva.api.dto.UsuarioCreateDTO;
import com.gestiondeportiva.api.dto.UsuarioDTO;
import com.gestiondeportiva.api.security.JwtUtil;
import com.gestiondeportiva.api.security.UserDetailsServiceImpl;
import com.gestiondeportiva.api.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsServiceImpl userDetailsService,
                          JwtUtil jwtUtil,
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        // Autenticar email + password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Cargar datos completos del usuario
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        // Generar token
        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // Verificar si el email ya existe
            if (usuarioService.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El email ya está registrado");
            }

            // Crear DTO para el servicio
            UsuarioCreateDTO nuevoUsuario = new UsuarioCreateDTO(
                    request.getNombre(),
                    request.getApellidos(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getRol(),
                    request.getPosicion(),
                    request.getTelefono(),
                    request.getIdEquipo()
            );

            // Guardar el usuario (la contraseña se encriptará en el servicio)
            UsuarioDTO usuarioCreado = usuarioService.save(nuevoUsuario);

            // Autenticar automáticamente después del registro
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Cargar datos completos del usuario
            UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

            // Generar token
            String token = jwtUtil.generateToken(user);

            // Devolver respuesta con token y datos del usuario
            RegisterResponse response = new RegisterResponse(token, usuarioCreado);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }
}
