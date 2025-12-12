package com.gestiondeportiva.api.auth;

/**
 * Clase que representa una solicitud de inicio de sesión (login).
 * <p>
 * Contiene las credenciales del usuario que intenta autenticarse en el sistema.
 * Se utiliza como objeto de entrada en el endpoint POST /api/auth/login.
 * </p>
 *
 * <p><strong>Campos requeridos:</strong></p>
 * <ul>
 *   <li>email: Dirección de correo electrónico del usuario</li>
 *   <li>password: Contraseña en texto plano (será validada con BCrypt)</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.auth.AuthController#login(LoginRequest)
 */
public class LoginRequest {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
