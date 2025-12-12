package com.gestiondeportiva.api.auth;

/**
 * Clase que representa la respuesta exitosa de un inicio de sesión (login).
 * <p>
 * Contiene el token JWT generado tras una autenticación exitosa.
 * Este token debe ser incluido en las cabeceras Authorization de las
 * peticiones posteriores para acceder a endpoints protegidos.
 * </p>
 *
 * <p><strong>Formato del token:</strong></p>
 * <ul>
 *   <li>Tipo: JWT (JSON Web Token)</li>
 *   <li>Contenido: Email del usuario, rol y fecha de expiración</li>
 *   <li>Uso: Header Authorization: Bearer {token}</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.auth.AuthController#login(LoginRequest)
 * @see com.gestiondeportiva.api.security.JwtUtil
 */
public class LoginResponse {

    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
