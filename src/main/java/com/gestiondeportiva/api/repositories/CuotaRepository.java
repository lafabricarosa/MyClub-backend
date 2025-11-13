package com.gestiondeportiva.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Cuota;
import com.gestiondeportiva.api.entities.EstadoCuota;


public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    List<Cuota> findByJugadorId(Long jugadorId);

    List<Cuota> findByEstadoCuota(EstadoCuota estadoCuota);

}
