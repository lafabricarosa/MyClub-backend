package com.gestiondeportiva.api.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gestiondeportiva.api.entities.Rol;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Utilidad de seguridad para gestionar el acceso basado en roles y propiedad de recursos.
 * <p>
 * Proporciona métodos auxiliares para obtener información del usuario autenticado y
 * validar permisos de acceso según las reglas de negocio del sistema MyClub. Complementa
 * las anotaciones @PreAuthorize con lógica de control de acceso más compleja.
 * </p>
 *
 * <p><strong>Reglas de acceso implementadas:</strong></p>
 * <ul>
 *   <li>ADMIN: Acceso total sin restricciones</li>
 *   <li>ENTRENADOR: Solo puede gestionar jugadores de su equipo</li>
 *   <li>JUGADOR: Solo puede ver y modificar sus propios datos</li>
 * </ul>
 *
 * <p><strong>Uso típico:</strong></p>
 * <pre>
 * // En un servicio
 * Usuario usuarioActual = securityUtils.getUsuarioActual();
 * securityUtils.checkEntrenadorSoloJugadoresDeSuEquipo(jugadorConsultado);
 * </pre>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.security.JwtAuthFilter
 */
@Component
public class SecurityUtils {

    private final UsuarioRepository usuarioRepository;

    public SecurityUtils(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene el email del usuario autenticado actualmente.
     * <p>
     * Extrae el email del SecurityContext establecido por JwtAuthFilter.
     * </p>
     *
     * @return String con el email del usuario autenticado
     * @throws AccessDeniedException si no hay usuario autenticado
     */
    public String getEmailActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new AccessDeniedException("No hay usuario autenticado");
        }
        return auth.getName(); // El JWT usa email como username
    }

    /**
     * Obtiene la entidad Usuario completa del usuario autenticado.
     * <p>
     * Busca el usuario en la base de datos usando el email extraído del SecurityContext.
     * </p>
     *
     * @return Usuario entidad completa del usuario autenticado
     * @throws EntityNotFoundException si el usuario no se encuentra en la base de datos
     */
    public Usuario getUsuarioActual() {
        return usuarioRepository.findByEmail(getEmailActual())
                .orElseThrow(() -> new EntityNotFoundException("Usuario actual no encontrado"));
    }

    /**
     * Verifica si el usuario autenticado tiene rol JUGADOR.
     *
     * @return true si es JUGADOR, false en caso contrario
     */
    public boolean esJugadorActual() {
        return getUsuarioActual().getRol() == Rol.JUGADOR;
    }

    /**
     * Verifica si el usuario autenticado tiene rol ENTRENADOR.
     *
     * @return true si es ENTRENADOR, false en caso contrario
     */
    public boolean esEntrenadorActual() {
        return getUsuarioActual().getRol() == Rol.ENTRENADOR;
    }

    /**
     * Verifica si el usuario autenticado tiene rol ADMIN.
     *
     * @return true si es ADMIN, false en caso contrario
     */
    public boolean esAdminActual() {
        return getUsuarioActual().getRol() == Rol.ADMIN;
    }

    /**
     * Valida que un jugador solo pueda acceder a sus propios datos.
     * <p>
     * Si el usuario autenticado es JUGADOR, verifica que el usuario consultado
     * sea él mismo. ADMIN y ENTRENADOR no tienen esta restricción.
     * </p>
     *
     * @param usuarioConsultado usuario que se intenta consultar
     * @throws AccessDeniedException si un JUGADOR intenta ver datos de otro usuario
     */
    public void checkJugadorSoloPuedeVerseASiMismo(Usuario usuarioConsultado) {
        if (esJugadorActual()) {
            if (!usuarioConsultado.getEmail().equals(getEmailActual())) {
                throw new AccessDeniedException("No puedes ver los datos de otro jugador");
            }
        }
    }

    /**
     * Valida que un entrenador solo pueda gestionar jugadores de su equipo.
     * <p>
     * Si el usuario autenticado es ENTRENADOR, verifica que el jugador consultado
     * pertenezca al mismo equipo. ADMIN no tiene esta restricción.
     * </p>
     *
     * @param jugadorConsultado jugador que se intenta gestionar
     * @throws AccessDeniedException si el entrenador intenta acceder a un jugador de otro equipo
     */
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

        if (!jugadorConsultado.getEquipo().getId()
                .equals(entrenador.getEquipo().getId())) {
            throw new AccessDeniedException("Este jugador no es de tu equipo");
        }
    }

    /**
     * Valida que solo usuarios ADMIN puedan realizar una acción.
     *
     * @throws AccessDeniedException si el usuario no es ADMIN
     */
    public void checkSoloAdmin() {
        if (!esAdminActual()) {
            throw new AccessDeniedException("Solo ADMIN puede realizar esta acción");
        }
    }
}
