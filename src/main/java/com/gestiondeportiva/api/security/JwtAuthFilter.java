package com.gestiondeportiva.api.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de autenticación JWT para Spring Security.
 * <p>
 * Intercepta todas las peticiones HTTP entrantes y valida los tokens JWT presentes
 * en el header Authorization. Si el token es válido, establece la autenticación en
 * el SecurityContext de Spring Security, permitiendo que los controllers accedan
 * a la información del usuario autenticado.
 * </p>
 *
 * <p><strong>Proceso de autenticación:</strong></p>
 * <ol>
 *   <li>Extrae el header Authorization de la petición HTTP</li>
 *   <li>Verifica que sea tipo Bearer token (formato: "Bearer {token}")</li>
 *   <li>Extrae el email del usuario del token JWT</li>
 *   <li>Carga los detalles completos del usuario desde la base de datos</li>
 *   <li>Crea un objeto de autenticación y lo establece en SecurityContext</li>
 *   <li>Continúa con la cadena de filtros</li>
 * </ol>
 *
 * <p><strong>Características:</strong></p>
 * <ul>
 *   <li>Hereda de OncePerRequestFilter: garantiza ejecución única por petición</li>
 *   <li>No valida expiración: asume tokens válidos (implementar si es necesario)</li>
 *   <li>Establece authorities desde UserDetails para control de acceso basado en roles</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.security.JwtUtil
 * @see com.gestiondeportiva.api.security.UserDetailsServiceImpl
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Procesa cada petición HTTP para autenticar mediante JWT.
     * <p>
     * Busca un token JWT en el header Authorization, lo valida y establece
     * la autenticación en SecurityContext si es válido.
     * </p>
     *
     * @param request petición HTTP entrante
     * @param response respuesta HTTP
     * @param filterChain cadena de filtros a continuar
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException si ocurre un error de I/O
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

