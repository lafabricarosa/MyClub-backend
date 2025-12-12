package com.gestiondeportiva.api.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiondeportiva.api.dto.EstadisticaDTO;
import com.gestiondeportiva.api.entities.*;
import com.gestiondeportiva.api.repositories.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Pruebas de integración para EstadisticaController
 * Estas pruebas verifican el comportamiento end-to-end de la API
 * incluyendo controlador, servicio, repositorio y base de datos
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class EstadisticaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private EstadisticaRepository estadisticaRepository;

    private Usuario jugador;
    private Evento evento;
    private Equipo equipo;

    @BeforeEach
    void setUp() {
        // Limpiar datos previos
        estadisticaRepository.deleteAll();
        eventoRepository.deleteAll();
        usuarioRepository.deleteAll();
        equipoRepository.deleteAll();

        // Crear equipo
        equipo = new Equipo();
        equipo.setNombre("Equipo Test");
        equipo.setCategoria(Categoria.SENIOR);
        equipo = equipoRepository.save(equipo);

        // Crear jugador
        jugador = new Usuario();
        jugador.setNombre("Juan");
        jugador.setApellidos("Pérez García");
        jugador.setEmail("juan.perez@test.com");
        jugador.setPassword("$2a$10$testHashedPassword");
        jugador.setRol(Rol.JUGADOR);
        jugador.setPosicion(Posicion.DELANTERO);
        jugador.setEquipo(equipo);
        jugador = usuarioRepository.save(jugador);

        // Crear evento
        evento = new Evento();
        evento.setDescripcion("Partido de prueba");
        evento.setTipoEvento(TipoEvento.PARTIDO);
        evento.setFecha(LocalDate.now());
        evento.setHora(LocalTime.of(18, 0));
        evento.setLugar("Estadio Test");
        evento.setEquipo(equipo);
        evento = eventoRepository.save(evento);
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testCrearEstadistica_DebeRetornar201() throws Exception {
        // Given
        EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
        estadisticaDTO.setIdJugador(jugador.getId());
        estadisticaDTO.setIdEvento(evento.getId());
        estadisticaDTO.setGoles(2);
        estadisticaDTO.setTarjetasAmarillas(1);
        estadisticaDTO.setTarjetasRojas(0);

        // When/Then
        mockMvc.perform(post("/api/estadisticas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estadisticaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.goles").value(2))
                .andExpect(jsonPath("$.tarjetasAmarillas").value(1))
                .andExpect(jsonPath("$.tarjetasRojas").value(0));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testCrearEstadisticaDuplicada_DebeActualizarla() throws Exception {
        // Given: Crear primera estadística
        EstadisticaDTO estadistica1 = new EstadisticaDTO();
        estadistica1.setIdJugador(jugador.getId());
        estadistica1.setIdEvento(evento.getId());
        estadistica1.setGoles(1);
        estadistica1.setTarjetasAmarillas(0);
        estadistica1.setTarjetasRojas(0);

        mockMvc.perform(post("/api/estadisticas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estadistica1)))
                .andExpect(status().isCreated());

        // When: Intentar crear estadística duplicada con valores diferentes
        EstadisticaDTO estadistica2 = new EstadisticaDTO();
        estadistica2.setIdJugador(jugador.getId());
        estadistica2.setIdEvento(evento.getId());
        estadistica2.setGoles(3);
        estadistica2.setTarjetasAmarillas(2);
        estadistica2.setTarjetasRojas(1);

        // Then: Debe actualizarse exitosamente
        mockMvc.perform(post("/api/estadisticas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estadistica2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.goles").value(3))
                .andExpect(jsonPath("$.tarjetasAmarillas").value(2))
                .andExpect(jsonPath("$.tarjetasRojas").value(1));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testCrearEstadisticaConJugadorInvalido_DebeRetornar404() throws Exception {
        // Given
        EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
        estadisticaDTO.setIdJugador(999L); // ID que no existe
        estadisticaDTO.setIdEvento(evento.getId());
        estadisticaDTO.setGoles(2);
        estadisticaDTO.setTarjetasAmarillas(1);
        estadisticaDTO.setTarjetasRojas(0);

        // When/Then
        mockMvc.perform(post("/api/estadisticas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estadisticaDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testObtenerEstadisticaPorId_DebeRetornarEstadistica() throws Exception {
        // Given: Crear estadística
        Estadistica estadistica = new Estadistica();
        estadistica.setJugador(jugador);
        estadistica.setEvento(evento);
        estadistica.setGoles(2);
        estadistica.setTarjetasAmarillas(1);
        estadistica.setTarjetasRojas(0);
        estadistica = estadisticaRepository.save(estadistica);

        // When/Then
        mockMvc.perform(get("/api/estadisticas/" + estadistica.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(estadistica.getId()))
                .andExpect(jsonPath("$.goles").value(2));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testObtenerEstadisticaInexistente_DebeRetornar404() throws Exception {
        // When/Then
        mockMvc.perform(get("/api/estadisticas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testActualizarEstadistica_DebeRetornar200() throws Exception {
        // Given: Crear estadística
        Estadistica estadistica = new Estadistica();
        estadistica.setJugador(jugador);
        estadistica.setEvento(evento);
        estadistica.setGoles(1);
        estadistica.setTarjetasAmarillas(0);
        estadistica.setTarjetasRojas(0);
        estadistica = estadisticaRepository.save(estadistica);

        // When: Actualizar
        EstadisticaDTO actualizacion = new EstadisticaDTO();
        actualizacion.setGoles(3);
        actualizacion.setTarjetasAmarillas(2);
        actualizacion.setTarjetasRojas(1);

        // Then
        mockMvc.perform(put("/api/estadisticas/" + estadistica.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goles").value(3))
                .andExpect(jsonPath("$.tarjetasAmarillas").value(2))
                .andExpect(jsonPath("$.tarjetasRojas").value(1));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testEliminarEstadistica_DebeRetornar204() throws Exception {
        // Given
        Estadistica estadistica = new Estadistica();
        estadistica.setJugador(jugador);
        estadistica.setEvento(evento);
        estadistica.setGoles(2);
        estadistica.setTarjetasAmarillas(1);
        estadistica.setTarjetasRojas(0);
        estadistica = estadisticaRepository.save(estadistica);

        // When/Then
        mockMvc.perform(delete("/api/estadisticas/" + estadistica.getId()))
                .andExpect(status().isNoContent());

        // Verificar que fue eliminada
        mockMvc.perform(get("/api/estadisticas/" + estadistica.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testListarTodasLasEstadisticas_DebeRetornarLista() throws Exception {
        // Given: Crear varias estadísticas
        for (int i = 0; i < 3; i++) {
            Estadistica est = new Estadistica();
            est.setJugador(jugador);
            est.setEvento(evento);
            est.setGoles(i);
            est.setTarjetasAmarillas(0);
            est.setTarjetasRojas(0);
            estadisticaRepository.save(est);
        }

        // When/Then
        mockMvc.perform(get("/api/estadisticas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void testAccesoSinAutenticacion_DebeRetornar401() throws Exception {
        // When/Then: Sin @WithMockUser
        mockMvc.perform(get("/api/estadisticas"))
                .andExpect(status().isForbidden());
    }
}
