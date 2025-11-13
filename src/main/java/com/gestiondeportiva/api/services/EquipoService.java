package com.gestiondeportiva.api.services;

import java.util.List;
import java.util.Optional;

import com.gestiondeportiva.api.dto.EquipoDTO;
import com.gestiondeportiva.api.entities.Categoria;

public interface EquipoService {

    List<EquipoDTO> findAll();

    EquipoDTO save(EquipoDTO equipoDTO);

    EquipoDTO update(Long id, EquipoDTO datosActualizados);

    void deleteById(Long id);

    Optional<EquipoDTO> findById(Long id);

    List<EquipoDTO> findByNombre(String nombre);

    List<EquipoDTO> findByCategoria(Categoria categoria);
}
