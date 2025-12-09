package com.gestiondeportiva.api.services;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gestiondeportiva.api.dto.UsuarioCreateDTO;
import com.gestiondeportiva.api.dto.UsuarioDTO;
import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.mappers.UsuarioMapper;
import com.gestiondeportiva.api.repositories.UsuarioRepository;
import com.gestiondeportiva.api.security.SecurityUtils;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder,
            SecurityUtils securityUtils) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.securityUtils = securityUtils;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN → devuelve todos
        if (securityUtils.esAdminActual()) {
            return usuarioMapper.toDTOList(usuarioRepository.findAll());
        }

        // ENTRENADOR → solo jugadores de su equipo
        if (securityUtils.esEntrenadorActual()) {
            Long idEquipo = actual.getEquipo().getId();
            return usuarioMapper.toDTOList(usuarioRepository.findByEquipoId(idEquipo));
        }

        // JUGADOR → NO PERMITIDO
        throw new AccessDeniedException("No puedes ver todos los usuarios");
    }

    @Override
    @Transactional
    public UsuarioDTO save(UsuarioCreateDTO nuevoUsuario) {

        // 1️⃣ Validar que el email no esté ya registrado
        usuarioRepository.findByEmail(nuevoUsuario.getEmail())
                .ifPresent(usuario -> {
                    throw new IllegalArgumentException("Ya existe un usuario con el email: " + nuevoUsuario.getEmail());
                });

        // 2️⃣ Obtener quién está creando el usuario (ADMIN o ENTRENADOR)
        Usuario usuarioActual = securityUtils.getUsuarioActual();

        // 3️⃣ Si es ENTRENADOR → solo puede crear JUGADORES de su propio equipo
        if (securityUtils.esEntrenadorActual()) {

            // Aseguramos que el nuevo usuario tenga rol JUGADOR
            if (nuevoUsuario.getRol() == null || nuevoUsuario.getRol() != Rol.JUGADOR) {
                throw new AccessDeniedException("Un entrenador solo puede crear jugadores");
            }

            // Aseguramos que el jugador pertenezca al equipo del entrenador
            if (usuarioActual.getEquipo() == null) {
                throw new AccessDeniedException("El entrenador no tiene equipo asignado");
            }

            // Aquí depende de cómo esté tu DTO:
            // Si UsuarioCreateDTO tiene un campo equipoId, mejor ignorar lo que venga del
            // cliente
            // y asignar SIEMPRE el equipo del entrenador:
            nuevoUsuario.setIdEquipo(usuarioActual.getEquipo().getId());
        }

        // 4️⃣ Si es JUGADOR → NO puede crear usuarios
        if (securityUtils.esJugadorActual()) {
            throw new AccessDeniedException("Un jugador no puede crear usuarios");
        }

        // 5️⃣ Mapear y guardar
        Usuario usuario = usuarioMapper.toEntity(nuevoUsuario);

        // Asignar equipo del entrenador en caso necesario (por si el mapper no lo hace
        // solo con equipoId)
        if (securityUtils.esEntrenadorActual()) {
            usuario.setEquipo(usuarioActual.getEquipo());
        }

        // 6️⃣ Cifrar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario guardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO datosActualizados) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        securityUtils.checkJugadorSoloPuedeVerseASiMismo(usuarioExistente);
        securityUtils.checkEntrenadorSoloJugadoresDeSuEquipo(usuarioExistente);

        usuarioMapper.updateEntityFromDTO(datosActualizados, usuarioExistente);

        return usuarioMapper.toDTO(usuarioRepository.save(usuarioExistente));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        // Comprobar que existe
        Usuario usuarioABorrar = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ID: " + id));

        Usuario usuarioActual = securityUtils.getUsuarioActual();

        // 👑 ADMIN: puede borrar a cualquiera
        if (securityUtils.esAdminActual()) {
            usuarioRepository.delete(usuarioABorrar);
            return;
        }

        // 👨‍🏫 ENTRENADOR: solo puede borrar jugadores de su equipo
        if (securityUtils.esEntrenadorActual()) {

            // Solo puede borrar JUGADORES
            if (usuarioABorrar.getRol() != Rol.JUGADOR) {
                throw new AccessDeniedException(
                        "Un entrenador solo puede eliminar jugadores, no otros entrenadores ni admins");
            }

            // Debe pertenecer a su equipo
            if (usuarioActual.getEquipo() == null ||
                    usuarioABorrar.getEquipo() == null ||
                    !usuarioActual.getEquipo().getId().equals(usuarioABorrar.getEquipo().getId())) {

                throw new AccessDeniedException("No puedes eliminar jugadores que no pertenecen a tu equipo");
            }

            usuarioRepository.delete(usuarioABorrar);
            return;
        }

        // 👨‍🎓 JUGADOR (u otro rol no contemplado): no puede borrar usuarios
        throw new AccessDeniedException("No tienes permisos para eliminar usuarios");
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findById(Long id) {

        Usuario usuarioConsultado = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // 🔐 Reglas centralizadas en SecurityUtils:
        // - si es JUGADOR → solo puede verse a sí mismo
        // - si es ENTRENADOR → solo jugadores de su equipo
        securityUtils.checkJugadorSoloPuedeVerseASiMismo(usuarioConsultado);
        securityUtils.checkEntrenadorSoloJugadoresDeSuEquipo(usuarioConsultado);

        return Optional.of(usuarioMapper.toDTO(usuarioConsultado));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findByEmail(String email) {

        Usuario consultado = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        securityUtils.checkJugadorSoloPuedeVerseASiMismo(consultado);
        securityUtils.checkEntrenadorSoloJugadoresDeSuEquipo(consultado);

        return Optional.of(usuarioMapper.toDTO(consultado));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByEquipoId(Long equipoId) {

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN → sin restricciones
        if (securityUtils.esAdminActual()) {
            return usuarioMapper.toDTOList(usuarioRepository.findByEquipoId(equipoId));
        }

        // JUGADOR → no permitido
        if (securityUtils.esJugadorActual()) {
            throw new AccessDeniedException("Un jugador no puede listar usuarios de un equipo");
        }

        // ENTRENADOR → solo su propio equipo
        if (securityUtils.esEntrenadorActual()) {
            Long equipoActual = actual.getEquipo().getId();

            if (!equipoActual.equals(equipoId)) {
                throw new AccessDeniedException("No puedes acceder a jugadores de otro equipo");
            }

            return usuarioMapper.toDTOList(usuarioRepository.findByEquipoId(equipoId));
        }

        // Cualquier otro rol → denegado
        throw new AccessDeniedException("No autorizado");
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByRol(Rol rol) {

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN → sin límites
        if (securityUtils.esAdminActual()) {
            return usuarioMapper.toDTOList(usuarioRepository.findByRol(rol));
        }

        // JUGADOR → nunca permitido
        if (securityUtils.esJugadorActual()) {
            throw new AccessDeniedException("Un jugador no puede buscar por rol");
        }

        // ENTRENADOR → solo puede buscar jugadores
        if (securityUtils.esEntrenadorActual()) {

            if (rol != Rol.JUGADOR) {
                throw new AccessDeniedException("Un entrenador solo puede ver jugadores");
            }

            Long idEquipo = actual.getEquipo().getId();

            return usuarioMapper.toDTOList(
                    usuarioRepository.findByEquipoId(idEquipo));
        }

        throw new AccessDeniedException("No autorizado");
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByApellidos(String apellidos) {

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN
        if (securityUtils.esAdminActual()) {
            return usuarioMapper.toDTOList(usuarioRepository.findByApellidos(apellidos));
        }

        // JUGADOR → no permitido
        if (securityUtils.esJugadorActual()) {
            throw new AccessDeniedException("Un jugador no puede buscar por apellidos");
        }

        // ENTRENADOR → solo jugadores de su equipo
        List<Usuario> jugadoresEquipo = usuarioRepository.findByEquipoId(actual.getEquipo().getId());

        return usuarioMapper.toDTOList(
                jugadoresEquipo.stream()
                        .filter(u -> apellidos.equalsIgnoreCase(u.getApellidos()))
                        .toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByPosicion(Posicion posicion) {

        Usuario actual = securityUtils.getUsuarioActual();

        // ADMIN
        if (securityUtils.esAdminActual()) {
            return usuarioMapper.toDTOList(usuarioRepository.findByPosicion(posicion));
        }

        // JUGADOR → no permitido
        if (securityUtils.esJugadorActual()) {
            throw new AccessDeniedException("Un jugador no puede buscar por posición");
        }

        // ENTRENADOR → solo jugadores de su equipo
        List<Usuario> jugadoresEquipo = usuarioRepository.findByEquipoId(actual.getEquipo().getId());

        return usuarioMapper.toDTOList(
                jugadoresEquipo.stream()
                        .filter(u -> posicion.equals(u.getPosicion()))
                        .toList());
    }

    @Override
    @Transactional
    public void cambiarPassword(Long id, String passwordActual, String passwordNueva) {
        // Buscar el usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Verificar que la contraseña actual es correcta
        if (!passwordEncoder.matches(passwordActual, usuario.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual no es correcta");
        }

        // Validar que la nueva contraseña no esté vacía
        if (passwordNueva == null || passwordNueva.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
        }

        // Validar longitud mínima (opcional)
        if (passwordNueva.length() < 6) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres");
        }

        // Cifrar y guardar la nueva contraseña
        usuario.setPassword(passwordEncoder.encode(passwordNueva));
        usuarioRepository.save(usuario);
    }
}