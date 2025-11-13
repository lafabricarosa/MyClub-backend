package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.EstadisticaDTO;

public interface EstadisticaService {


    List<EstadisticaDTO> findAll();

    EstadisticaDTO save(EstadisticaDTO estadisticaDTO);

    EstadisticaDTO update(Long id, EstadisticaDTO datosActualizados);

    void deleteById(Long id);

    List<EstadisticaDTO> findByJugadorId(Long jugadorId);

    List<EstadisticaDTO> findByEventoId(Long eventoId);

    Optional<EstadisticaDTO> findById(Long id);


}
