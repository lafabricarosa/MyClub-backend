package com.gestiondeportiva.api.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Utilidad para la generación y validación de tokens JWT (JSON Web Tokens).
 * <p>
 * Proporciona métodos para crear tokens JWT firmados, extraer información (claims)
 * de tokens existentes y validar su expiración. Utiliza la librería JJWT para
 * manejar tokens según el estándar RFC 7519.
 * </p>
 *
 * <p><strong>Características del token:</strong></p>
 * <ul>
 *   <li>Algoritmo: HS256 (HMAC con SHA-256)</li>
 *   <li>Longitud de clave: 256 bits</li>
 *   <li>Validez: 1 día (86400000 ms)</li>
 *   <li>Subject: Email del usuario</li>
 *   <li>Claims personalizados: roles del usuario</li>
 * </ul>
 *
 * <p><strong>Seguridad:</strong></p>
 * <ul>
 *   <li>⚠️ La clave secreta está hardcodeada (solo para desarrollo)</li>
 *   <li>En producción: usar variables de entorno o servicios de gestión de secretos</li>
 *   <li>Nunca exponer la clave en repositorios públicos</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.security.JwtAuthFilter
 * @see com.gestiondeportiva.api.auth.AuthController
 */
@Component
public class JwtUtil {

    // Clave de al menos 32 caracteres (256 bits) para HS256
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "clave_super_segura_de_al_menos_32_bytes_123456".getBytes(StandardCharsets.UTF_8)
    );

    /**
     * Genera un token JWT para un usuario autenticado.
     * <p>
     * El token incluye el email del usuario como subject, sus roles como claim personalizado,
     * y tiene una validez de 1 día desde el momento de generación.
     * </p>
     *
     * @param userDetails datos del usuario autenticado (email y authorities)
     * @return String con el token JWT firmado
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())          // subject = email/username
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Extrae el username (email) del token JWT.
     *
     * @param token token JWT del que extraer el username
     * @return String con el email del usuario
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Verifica si un token JWT ha expirado.
     *
     * @param token token JWT a validar
     * @return true si el token ha expirado, false en caso contrario
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     *
     * @param token token JWT del que extraer la fecha
     * @return Date con la fecha de expiración
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim específico del token JWT usando una función resolvente.
     *
     * @param <T> tipo del claim a extraer
     * @param token token JWT del que extraer el claim
     * @param claimsResolver función que extrae el claim deseado de Claims
     * @return valor del claim extraído
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims (payload) del token JWT.
     * <p>
     * Verifica la firma del token usando la clave secreta antes de devolver los claims.
     * </p>
     *
     * @param token token JWT a parsear
     * @return Claims con toda la información del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
