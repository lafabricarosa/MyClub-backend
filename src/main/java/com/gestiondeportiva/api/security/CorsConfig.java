package com.gestiondeportiva.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración CORS deshabilitada.
 * CORS se maneja ahora desde SecurityConfig.java para evitar conflictos.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // La configuración CORS se maneja en SecurityConfig.java
}