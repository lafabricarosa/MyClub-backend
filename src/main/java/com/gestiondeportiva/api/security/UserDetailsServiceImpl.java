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

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

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
