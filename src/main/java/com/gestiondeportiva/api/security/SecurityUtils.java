package com.gestiondeportiva.api.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gestiondeportiva.api.entities.Rol;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class SecurityUtils {

    private final UsuarioRepository usuarioRepository;

    public SecurityUtils(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ============================================================
    // 🔐 Obtener info del usuario autenticado
    // ============================================================

    public String getEmailActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new AccessDeniedException("No hay usuario autenticado");
        }
        return auth.getName(); // El JWT usa email como username
    }

    public Usuario getUsuarioActual() {
        return usuarioRepository.findByEmail(getEmailActual())
                .orElseThrow(() -> new EntityNotFoundException("Usuario actual no encontrado"));
    }

    // ============================================================
    // 🔐 Comprobaciones de rol
    // ============================================================

    public boolean esJugadorActual() {
        return getUsuarioActual().getRol() == Rol.JUGADOR;
    }

    public boolean esEntrenadorActual() {
        return getUsuarioActual().getRol() == Rol.ENTRENADOR;
    }

    public boolean esAdminActual() {
        return getUsuarioActual().getRol() == Rol.ADMIN;
    }

    // ============================================================
    // 🔐 Validaciones de acceso por propiedad
    // ============================================================

    // 1️⃣ JUGADOR: solo puede verse a sí mismo
    public void checkJugadorSoloPuedeVerseASiMismo(Usuario usuarioConsultado) {
        if (esJugadorActual()) {
            if (!usuarioConsultado.getEmail().equals(getEmailActual())) {
                throw new AccessDeniedException("No puedes ver los datos de otro jugador");
            }
        }
    }

    // 2️⃣ ENTRENADOR: solo puede gestionar jugadores de su equipo
    public void checkEntrenadorSoloJugadoresDeSuEquipo(Usuario jugadorConsultado) {

        if (!esEntrenadorActual()) {
            return; // No aplica la regla si no es entrenador
        }

        Usuario entrenador = getUsuarioActual();

        if (entrenador.getEquipo() == null) {
            throw new AccessDeniedException("El entrenador no tiene equipo asignado");
        }

        if (jugadorConsultado.getEquipo() == null) {
            throw new AccessDeniedException("Este jugador no pertenece a ningún equipo");
        }

        // ⚠️ CLAVE: comparar equipos
        if (!jugadorConsultado.getEquipo().getId()
                .equals(entrenador.getEquipo().getId())) {
            throw new AccessDeniedException("Este jugador no es de tu equipo");
        }
    }

    // ============================================================
    // 🔐 Regla universal: ADMIN siempre permitido
    // ============================================================
    public void checkSoloAdmin() {
        if (!esAdminActual()) {
            throw new AccessDeniedException("Solo ADMIN puede realizar esta acción");
        }
    }

}
