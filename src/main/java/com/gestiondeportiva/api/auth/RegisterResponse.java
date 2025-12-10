package com.gestiondeportiva.api.auth;

import com.gestiondeportiva.api.dto.UsuarioDTO;

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
