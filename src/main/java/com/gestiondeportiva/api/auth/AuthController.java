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

/**
 * Controlador REST para autenticación y registro de usuarios.
 * <p>
 * Proporciona endpoints públicos (sin autenticación requerida) para login y registro.
 * Genera tokens JWT para usuarios autenticados que se utilizan en llamadas posteriores a la API.
 * </p>
 *
 * <p><strong>Endpoints:</strong></p>
 * <ul>
 *   <li>POST /api/auth/login - Autentica usuario y devuelve token JWT</li>
 *   <li>POST /api/auth/register - Registra nuevo usuario y devuelve token JWT</li>
 * </ul>
 *
 * <p><strong>Flujo de autenticación:</strong></p>
 * <ol>
 *   <li>Cliente envía email y contraseña</li>
 *   <li>Spring Security verifica credenciales (contraseña con BCrypt)</li>
 *   <li>Si es correcto, genera token JWT con rol del usuario</li>
 *   <li>Cliente usa el token en header Authorization para llamadas posteriores</li>
 * </ol>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see JwtUtil
 * @see UserDetailsServiceImpl
 */
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

    /**
     * Autentica un usuario y genera un token JWT.
     * <p>
     * Valida las credenciales (email y contraseña) contra la base de datos.
     * La contraseña se compara con BCrypt. Si es correcta, genera un token JWT
     * que incluye el rol del usuario y tiene una validez configurable.
     * </p>
     *
     * @param request objeto con email y password del usuario
     * @return ResponseEntity con LoginResponse conteniendo el token JWT
     * @throws org.springframework.security.core.AuthenticationException si las credenciales son incorrectas
     */
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

    /**
     * Registra un nuevo usuario en el sistema y autentica automáticamente.
     * <p>
     * Crea un nuevo usuario con los datos proporcionados, encripta la contraseña,
     * y genera un token JWT automáticamente sin requerir un login adicional.
     * </p>
     *
     * <p><strong>Validaciones:</strong></p>
     * <ul>
     *   <li>Verifica que el email no esté ya registrado</li>
     *   <li>Aplica validaciones de @Valid en RegisterRequest</li>
     *   <li>Encripta contraseña con BCrypt antes de guardar</li>
     * </ul>
     *
     * @param request datos del nuevo usuario con validaciones
     * @return ResponseEntity con RegisterResponse (token y datos del usuario) y código 201,
     *         o 409 si el email ya existe
     */
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
