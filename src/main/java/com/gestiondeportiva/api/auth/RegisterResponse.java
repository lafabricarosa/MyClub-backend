package com.gestiondeportiva.api.auth;

import com.gestiondeportiva.api.dto.UsuarioDTO;

/**
 * Clase que representa la respuesta exitosa de un registro de usuario.
 * <p>
 * Contiene tanto el token JWT generado como los datos del usuario recién creado.
 * Esto permite que el frontend autentique automáticamente al usuario tras el registro
 * sin necesidad de hacer un login adicional.
 * </p>
 *
 * <p><strong>Contenido de la respuesta:</strong></p>
 * <ul>
 *   <li>token: Token JWT para autenticar futuras peticiones</li>
 *   <li>usuario: Datos completos del usuario registrado (sin la contraseña)</li>
 * </ul>
 *
 * <p><strong>Flujo típico:</strong></p>
 * <ol>
 *   <li>Usuario envía RegisterRequest al backend</li>
 *   <li>Backend crea la cuenta y encripta la contraseña</li>
 *   <li>Backend genera token JWT y lo devuelve junto con los datos del usuario</li>
 *   <li>Frontend almacena el token y redirige al usuario a la aplicación</li>
 * </ol>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.auth.AuthController#register(RegisterRequest)
 */
public class RegisterResponse {

    private String token;
    private UsuarioDTO usuario;

    public RegisterResponse() {
    }

    public RegisterResponse(String token, UsuarioDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
