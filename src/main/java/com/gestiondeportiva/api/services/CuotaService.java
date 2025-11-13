package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.CuotaDTO;
import com.gestiondeportiva.api.entities.EstadoCuota;

public interface CuotaService {

    List<CuotaDTO> findAll();

    CuotaDTO save (CuotaDTO cuotadto);

    CuotaDTO update(Long id, CuotaDTO cuotaDTO);

    void deleteById(Long id);

    Optional<CuotaDTO> findById(Long id);

    List<CuotaDTO> findByJugadorId(Long jugadorId);

    List<CuotaDTO> findByEstadoCuota(EstadoCuota estadoCuota);

}
