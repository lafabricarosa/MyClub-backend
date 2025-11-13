package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.EquipoDTO;
import com.gestiondeportiva.api.entities.Categoria;
import com.gestiondeportiva.api.entities.Equipo;
import com.gestiondeportiva.api.mappers.EquipoMapper;
import com.gestiondeportiva.api.repositories.EquipoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final EquipoMapper equipoMapper;

    public EquipoServiceImpl(EquipoRepository equipoRepository, EquipoMapper equipoMapper) {
        this.equipoRepository = equipoRepository;
        this.equipoMapper = equipoMapper;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<EquipoDTO> findAll() {
        return equipoMapper.toDTOList(equipoRepository.findAll());
    }

    @Override
    @Transactional
    public EquipoDTO save(EquipoDTO equipoDTO) {
        if (equipoDTO == null) {
            throw new IllegalArgumentException("El equipo no puede ser nulo");
        }

        if (equipoDTO.getNombre() == null) {
            throw new IllegalArgumentException("Debe indicar el nombre del equipo");
        }

        Equipo equipo = equipoMapper.toEntity(equipoDTO);
        Equipo guardado = equipoRepository.save(equipo);
        return equipoMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public EquipoDTO update(Long id, EquipoDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");
        }
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + id));

        equipoMapper.updateEntityFromDTO(datosActualizados, equipo);
        Equipo actualizado = equipoRepository.save(equipo);
        return equipoMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipo no encontrado con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipoDTO> findById(Long id) {
        return equipoRepository.findById(id).map(equipo -> equipoMapper.toDTO(equipo));

    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoDTO> findByNombre(String nombre) {
        return equipoMapper.toDTOList(equipoRepository.findByNombre(nombre));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoDTO> findByCategoria(Categoria categoria) {
        return equipoMapper.toDTOList(equipoRepository.findByCategoria(categoria));
    }

}
