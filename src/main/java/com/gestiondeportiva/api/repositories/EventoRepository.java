package com.gestiondeportiva.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.TipoEvento;

import java.util.List;
import java.time.LocalDate;

public interface EventoRepository extends JpaRepository<Evento, Long>   {

    List<Evento> findByTipoEvento(TipoEvento tipoEvento);

    List<Evento> findByEquipoId(Long equipoId);

    List<Evento> findByFecha(LocalDate fecha);

    List<Evento> findByFechaAfter(LocalDate fecha);

    List<Evento> findByLugar(String lugar);
}
