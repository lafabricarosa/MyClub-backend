package com.gestiondeportiva.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración CORS deshabilitada - Manejada desde SecurityConfig.
 * <p>
 * Esta clase está vacía intencionalmente. La configuración CORS se maneja
 * completamente desde {@link SecurityConfig#corsConfigurationSource()} para
 * evitar conflictos entre múltiples configuraciones CORS.
 * </p>
 *
 * <p><strong>Razón del cambio:</strong></p>
 * <ul>
 *   <li>Spring Security tiene su propio sistema CORS que debe usarse cuando hay autenticación</li>
 *   <li>Configurar CORS en dos lugares puede causar conflictos de cabeceras</li>
 *   <li>La configuración en SecurityConfig se aplica antes de los filtros de seguridad</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see SecurityConfig#corsConfigurationSource()
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // La configuración CORS se maneja en SecurityConfig.java
}