package com.gestiondeportiva.api.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.repositories.UsuarioRepository;

/**
 * Implementación de UserDetailsService de Spring Security para autenticación.
 * <p>
 * Carga los detalles del usuario desde la base de datos mediante el email y
 * los convierte al formato requerido por Spring Security. Los roles se mapean
 * con el prefijo ROLE_ (ej: ROLE_ADMIN, ROLE_ENTRENADOR, ROLE_JUGADOR).
 * </p>
 *
 * <p><strong>Proceso de autenticación:</strong></p>
 * <ol>
 *   <li>Spring Security llama a loadUserByUsername con el email del usuario</li>
 *   <li>Se busca el usuario en la base de datos por email</li>
 *   <li>Se extraen el rol y la contraseña (ya encriptada con BCrypt)</li>
 *   <li>Se retorna un UserDetails con email, password y authorities</li>
 * </ol>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see UserDetailsService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga los detalles del usuario por su email (username).
     * <p>
     * Este método es invocado automáticamente por Spring Security durante el proceso
     * de autenticación para obtener la información del usuario desde la base de datos.
     * </p>
     *
     * <p><strong>Proceso:</strong></p>
     * <ol>
     *   <li>Busca el usuario en la base de datos por email</li>
     *   <li>Extrae el rol y lo convierte a authority con prefijo ROLE_</li>
     *   <li>Crea y retorna un UserDetails con email, password cifrada y authorities</li>
     * </ol>
     *
     * @param email dirección de correo electrónico del usuario (usado como username)
     * @return UserDetails con la información del usuario para Spring Security
     * @throws UsernameNotFoundException si no se encuentra un usuario con ese email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con email: " + email)
                );

        // Suponiendo que tu entidad Usuario tiene un campo `rol` de tipo enum Rol
        // y valores como ADMIN, ENTRENADOR, JUGADOR, etc.
        String rolNombre = usuario.getRol().name(); // p.ej. "ADMIN"

        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + rolNombre));

        return new User(
                usuario.getEmail(),      // username (email)
                usuario.getPassword(),   // password BCRYPT
                authorities              // roles
        );
    }
}
