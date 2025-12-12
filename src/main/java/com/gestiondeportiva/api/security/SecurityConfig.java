package com.gestiondeportiva.api.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración central de Spring Security para el sistema MyClub.
 * <p>
 * Define la política de seguridad de la aplicación, incluyendo autenticación JWT,
 * autorización basada en roles, configuración CORS y manejo de sesiones stateless.
 * Habilita el uso de anotaciones @PreAuthorize en los controllers.
 * </p>
 *
 * <p><strong>Características de seguridad:</strong></p>
 * <ul>
 *   <li>Autenticación JWT sin estado (stateless)</li>
 *   <li>Encriptación de contraseñas con BCrypt</li>
 *   <li>CSRF deshabilitado (apropiado para APIs REST)</li>
 *   <li>CORS configurado para frontend en Vercel y localhost</li>
 *   <li>Endpoints públicos: /api/auth/login, /api/auth/register, /uploads/**</li>
 * </ul>
 *
 * <p><strong>Flujo de seguridad:</strong></p>
 * <ol>
 *   <li>JwtAuthFilter intercepta peticiones y valida tokens</li>
 *   <li>Si el token es válido, establece autenticación en SecurityContext</li>
 *   <li>@PreAuthorize evalúa permisos basados en roles</li>
 *   <li>Si está autorizado, el controller procesa la petición</li>
 * </ol>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.security.JwtAuthFilter
 * @see com.gestiondeportiva.api.security.UserDetailsServiceImpl
 */
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
            JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Configura el codificador de contraseñas BCrypt.
     * <p>
     * BCrypt es un algoritmo de hash adaptativo que incluye salt automático
     * y permite ajustar la complejidad computacional.
     * </p>
     *
     * @return PasswordEncoder configurado con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el gestor de autenticación de Spring Security.
     * <p>
     * Utilizado en AuthController para autenticar credenciales de usuario.
     * </p>
     *
     * @param config configuración de autenticación de Spring Security
     * @return AuthenticationManager configurado
     * @throws Exception si hay error en la configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura la cadena de filtros de seguridad.
     * <p>
     * Define qué endpoints son públicos, aplica el filtro JWT y establece
     * la política de sesiones stateless apropiada para APIs REST.
     * </p>
     *
     * @param http objeto HttpSecurity para configurar
     * @return SecurityFilterChain configurado
     * @throws Exception si hay error en la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura las reglas CORS (Cross-Origin Resource Sharing).
     * <p>
     * Permite peticiones desde el frontend desplegado en Vercel y desde
     * localhost para desarrollo. Habilita credenciales y expone el header
     * Authorization para que el frontend pueda leer tokens JWT.
     * </p>
     *
     * <p><strong>Orígenes permitidos:</strong></p>
     * <ul>
     *   <li>https://my-club-frontend.vercel.app (producción)</li>
     *   <li>http://localhost:4200 (desarrollo Angular)</li>
     *   <li>http://localhost:8080 (desarrollo alternativo)</li>
     * </ul>
     *
     * @return CorsConfigurationSource con la configuración CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
            "https://my-club-frontend.vercel.app",
            "http://localhost:4200",
            "http://localhost:8080"
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
