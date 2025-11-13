package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.ConvocatoriaDTO;

public interface ConvocatoriaService {

    List<ConvocatoriaDTO> findAll();

    ConvocatoriaDTO save(ConvocatoriaDTO convocatoriaDTO);

    ConvocatoriaDTO update(Long id, ConvocatoriaDTO datosActualizados);

    void deleteById(Long id);

    List<ConvocatoriaDTO> findByEventoId(Long eventoId);

    List<ConvocatoriaDTO> findByJugadorId(Long jugadorId);

    List<ConvocatoriaDTO> findByEventoIdAndTitularTrue(Long eventoId);

    Optional<ConvocatoriaDTO> findById(Long id);

}

