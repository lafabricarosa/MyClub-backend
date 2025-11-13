package com.gestiondeportiva.api.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.UsuarioCreateDTO;
import com.gestiondeportiva.api.dto.UsuarioDTO;
import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.mappers.UsuarioMapper;
import com.gestiondeportiva.api.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    @Override
    @Transactional
    public UsuarioDTO save(UsuarioCreateDTO nuevoUsuario) {
        // Validar que el email no esté ya registrado
        usuarioRepository.findByEmail(nuevoUsuario.getEmail())
                .ifPresent(usuario -> {
                    throw new IllegalArgumentException("Ya existe un usuario con el email: " + nuevoUsuario.getEmail());
                });

        Usuario usuario = usuarioMapper.toEntity(nuevoUsuario);
        //usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario guardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");
        }
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        usuarioMapper.updateEntityFromDTO(datosActualizados, usuarioExistente);

        Usuario actualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe un usuario con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> usuarioMapper.toDTO(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> usuarioMapper.toDTO(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByEquipoId(Long equipoId) {
        return usuarioMapper.toDTOList(usuarioRepository.findByEquipoId(equipoId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByRol(Rol rol) {
        return usuarioMapper.toDTOList(usuarioRepository.findByRol(rol));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByApellidos(String apellidos) {
        return usuarioMapper.toDTOList(usuarioRepository.findByApellidos(apellidos));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByPosicion(Posicion posicion) {
        return usuarioMapper.toDTOList(usuarioRepository.findByPosicion(posicion));
    }

}
    
