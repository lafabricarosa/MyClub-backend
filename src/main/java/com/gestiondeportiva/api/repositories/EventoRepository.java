package com.gestiondeportiva.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.TipoEvento;

import java.util.List;
import java.time.LocalDate;

/**
 * Repositorio JPA para la gestión de entidades Evento.
 * <p>
 * Proporciona operaciones CRUD y consultas personalizadas para gestionar
 * eventos del calendario deportivo (entrenamientos, partidos, reuniones).
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Evento
 * @see JpaRepository
 */
public interface EventoRepository extends JpaRepository<Evento, Long> {

    /**
     * Busca eventos por tipo.
     *
     * @param tipoEvento tipo de evento (ENTRENAMIENTO, PARTIDO, REUNION)
     * @return lista de eventos del tipo especificado
     */
    List<Evento> findByTipoEvento(TipoEvento tipoEvento);

    /**
     * Busca todos los eventos de un equipo específico.
     *
     * @param equipoId ID del equipo
     * @return lista de eventos del equipo
     */
    List<Evento> findByEquipoId(Long equipoId);

    /**
     * Busca eventos en una fecha específica.
     *
     * @param fecha fecha del evento
     * @return lista de eventos en la fecha especificada
     */
    List<Evento> findByFecha(LocalDate fecha);

    /**
     * Busca eventos posteriores a una fecha determinada.
     * <p>
     * Útil para obtener eventos futuros del calendario.
     * </p>
     *
     * @param fecha fecha de referencia
     * @return lista de eventos después de la fecha especificada
     */
    List<Evento> findByFechaAfter(LocalDate fecha);

    /**
     * Busca eventos por ubicación.
     *
     * @param lugar ubicación del evento
     * @return lista de eventos en la ubicación especificada
     */
    List<Evento> findByLugar(String lugar);
}
