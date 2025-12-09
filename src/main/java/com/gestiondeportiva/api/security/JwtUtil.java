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

@Component
public class JwtUtil {

    // Clave de al menos 32 caracteres (256 bits) para HS256
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "clave_super_segura_de_al_menos_32_bytes_123456".getBytes(StandardCharsets.UTF_8)
    );

    // ====================== GENERAR TOKEN ======================
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())          // subject = email/username
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(SECRET_KEY)                           // ✅ válido en 0.11.5
                .compact();
    }

    // ====================== EXTRAER USERNAME ======================
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ====================== VALIDAR EXPIRACIÓN ======================
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ====================== CLAIMS GENÉRICO ======================
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()               // ✅ forma moderna en 0.11.5
                .setSigningKey(SECRET_KEY)        // ✅ reemplazo de parser().setSigningKey(...)
                .build()
                .parseClaimsJws(token)
                .getBody();                       // payload = Claims
    }
}
