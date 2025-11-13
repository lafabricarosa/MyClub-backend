package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestiondeportiva.api.dto.CuotaDTO;
import com.gestiondeportiva.api.entities.Cuota;
import com.gestiondeportiva.api.entities.EstadoCuota;
import com.gestiondeportiva.api.mappers.CuotaMapper;
import com.gestiondeportiva.api.repositories.CuotaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class CuotaServiceImpl implements CuotaService {

    private final CuotaRepository cuotaRepository;
    private final CuotaMapper cuotaMapper;

    public CuotaServiceImpl(CuotaRepository cuotaRepository, CuotaMapper cuotaMapper) {
        this.cuotaRepository = cuotaRepository;
        this.cuotaMapper = cuotaMapper;
    }

    // ================== CRUD ==================

    @Override
    @Transactional(readOnly = true)
    public List<CuotaDTO> findAll() {
        return cuotaMapper.toDTOList(cuotaRepository.findAll());
    }

    @Override
    @Transactional
    public CuotaDTO save(CuotaDTO cuotaDTO) {
        if (cuotaDTO == null) {
            throw new IllegalArgumentException("La cuota no puede ser nula");
        }

        if (cuotaDTO.getIdJugador() == null) {
            throw new IllegalArgumentException("Debe indicar el jugador asociado a la cuota");
        }

        if (cuotaDTO.getImporte() == null) {
            throw new IllegalArgumentException("Debe indicar el importe de la cuota");
        }

        if (cuotaDTO.getEstadoCuota() == null) {
            throw new IllegalArgumentException("Debe indicar el estado de la cuota");
        }

        Cuota cuota = cuotaMapper.toEntity(cuotaDTO);
        Cuota guardada = cuotaRepository.save(cuota);
        return cuotaMapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public CuotaDTO update(Long id, CuotaDTO datosActualizados) {
        if (datosActualizados == null) {
            throw new IllegalArgumentException("Los datos actualizados no pueden ser nulos");
        }
        Cuota cuotaDb = cuotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuota no encontrada con ID: " + id));

        cuotaMapper.updateEntityFromDTO(datosActualizados, cuotaDb);
        Cuota actualizada = cuotaRepository.save(cuotaDb);
        return cuotaMapper.toDTO(actualizada);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!cuotaRepository.existsById(id)) {
            throw new EntityNotFoundException("Cuota no encontrada con ID: " + id);
        }
        cuotaRepository.deleteById(id);
    }

    // ================== Filtros ==================

    @Override
    @Transactional(readOnly = true)
    public Optional<CuotaDTO> findById(Long id) {
        return cuotaRepository.findById(id).map(cuota -> cuotaMapper.toDTO(cuota));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuotaDTO> findByJugadorId(Long jugadorId) {
        return cuotaMapper.toDTOList(cuotaRepository.findByJugadorId(jugadorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuotaDTO> findByEstadoCuota(EstadoCuota estadoCuota) {
        return cuotaMapper.toDTOList(cuotaRepository.findByEstadoCuota(estadoCuota));
    }

}
