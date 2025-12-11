package com.gestiondeportiva.api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gestiondeportiva.api.dto.EstadisticaDTO;
import com.gestiondeportiva.api.entities.Estadistica;
import com.gestiondeportiva.api.entities.Evento;
import com.gestiondeportiva.api.entities.Usuario;
import com.gestiondeportiva.api.mappers.EstadisticaMapper;
import com.gestiondeportiva.api.repositories.EstadisticaRepository;
import com.gestiondeportiva.api.repositories.EventoRepository;
import com.gestiondeportiva.api.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Pruebas unitarias para EstadisticaServiceImpl
 * Estas pruebas verifican la lógica de negocio de forma aislada usando mocks
 */
@ExtendWith(MockitoExtension.class)
class EstadisticaServiceImplTest {

    @Mock
    private EstadisticaRepository estadisticaRepository;

    @Mock
    private EstadisticaMapper estadisticaMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EstadisticaServiceImpl estadisticaService;

    private Usuario jugadorMock;
    private Evento eventoMock;
    private EstadisticaDTO estadisticaDTO;
    private Estadistica estadisticaEntity;

    @BeforeEach
    void setUp() {
        // Crear objetos mock para las pruebas
        jugadorMock = new Usuario();
        jugadorMock.setId(1L);
        jugadorMock.setNombre("Juan Pérez");

        eventoMock = new Evento();
        eventoMock.setId(1L);
        eventoMock.setDescripcion("Partido vs Rival");

        estadisticaDTO = new EstadisticaDTO();
        estadisticaDTO.setIdJugador(1L);
        estadisticaDTO.setIdEvento(1L);
        estadisticaDTO.setGoles(2);
        estadisticaDTO.setTarjetasAmarillas(1);
        estadisticaDTO.setTarjetasRojas(0);

        estadisticaEntity = new Estadistica();
        estadisticaEntity.setId(1L);
        estadisticaEntity.setJugador(jugadorMock);
        estadisticaEntity.setEvento(eventoMock);
        estadisticaEntity.setGoles(2);
        estadisticaEntity.setTarjetasAmarillas(1);
        estadisticaEntity.setTarjetasRojas(0);
    }

    @Test
    void testSave_CuandoEsNuevaEstadistica_DebeCrearla() {
        // Given: No existe estadística previa
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(jugadorMock));
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(eventoMock));
        when(estadisticaRepository.findByEventoIdAndJugadorId(1L, 1L)).thenReturn(Optional.empty());
        when(estadisticaRepository.save(any(Estadistica.class))).thenReturn(estadisticaEntity);
        when(estadisticaMapper.toDTO(any(Estadistica.class))).thenReturn(estadisticaDTO);

        // When: Se guarda la estadística
        EstadisticaDTO resultado = estadisticaService.save(estadisticaDTO);

        // Then: Se crea una nueva estadística
        assertNotNull(resultado);
        assertEquals(2, resultado.getGoles());
        verify(estadisticaRepository, times(1)).save(any(Estadistica.class));
        verify(estadisticaRepository, times(1)).findByEventoIdAndJugadorId(1L, 1L);
    }

    @Test
    void testSave_CuandoYaExiste_DebeActualizarla() {
        // Given: Ya existe una estadística
        Estadistica estadisticaExistente = new Estadistica();
        estadisticaExistente.setId(1L);
        estadisticaExistente.setJugador(jugadorMock);
        estadisticaExistente.setEvento(eventoMock);
        estadisticaExistente.setGoles(0);
        estadisticaExistente.setTarjetasAmarillas(0);
        estadisticaExistente.setTarjetasRojas(0);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(jugadorMock));
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(eventoMock));
        when(estadisticaRepository.findByEventoIdAndJugadorId(1L, 1L))
                .thenReturn(Optional.of(estadisticaExistente));
        when(estadisticaRepository.save(any(Estadistica.class))).thenReturn(estadisticaExistente);
        when(estadisticaMapper.toDTO(any(Estadistica.class))).thenReturn(estadisticaDTO);

        // When: Se intenta guardar
        EstadisticaDTO resultado = estadisticaService.save(estadisticaDTO);

        // Then: Se actualiza la existente
        assertNotNull(resultado);
        verify(estadisticaRepository, times(1)).save(estadisticaExistente);
        assertEquals(2, estadisticaExistente.getGoles()); // Valores actualizados
    }

    @Test
    void testSave_CuandoJugadorNoExiste_DebeLanzarExcepcion() {
        // Given: El jugador no existe
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // When/Then: Debe lanzar EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            estadisticaService.save(estadisticaDTO);
        });

        verify(estadisticaRepository, never()).save(any());
    }

    @Test
    void testSave_CuandoEventoNoExiste_DebeLanzarExcepcion() {
        // Given: El evento no existe
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(jugadorMock));
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        // When/Then: Debe lanzar EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            estadisticaService.save(estadisticaDTO);
        });

        verify(estadisticaRepository, never()).save(any());
    }

    @Test
    void testSave_CuandoDTOEsNulo_DebeLanzarExcepcion() {
        // When/Then: Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            estadisticaService.save(null);
        });
    }

    @Test
    void testFindById_CuandoExiste_DebeRetornarla() {
        // Given
        when(estadisticaRepository.findById(1L)).thenReturn(Optional.of(estadisticaEntity));
        when(estadisticaMapper.toDTO(estadisticaEntity)).thenReturn(estadisticaDTO);

        // When
        Optional<EstadisticaDTO> resultado = estadisticaService.findById(1L);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdJugador());
    }

    @Test
    void testFindById_CuandoNoExiste_DebeRetornarVacio() {
        // Given
        when(estadisticaRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<EstadisticaDTO> resultado = estadisticaService.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    void testFindByJugadorId_DebeRetornarLista() {
        // Given
        List<Estadistica> estadisticas = Arrays.asList(estadisticaEntity);
        when(estadisticaRepository.findByJugadorId(1L)).thenReturn(estadisticas);
        when(estadisticaMapper.toDTOList(estadisticas)).thenReturn(Arrays.asList(estadisticaDTO));

        // When
        List<EstadisticaDTO> resultado = estadisticaService.findByJugadorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(estadisticaRepository, times(1)).findByJugadorId(1L);
    }

    @Test
    void testFindByEventoId_DebeRetornarLista() {
        // Given
        List<Estadistica> estadisticas = Arrays.asList(estadisticaEntity);
        when(estadisticaRepository.findByEventoId(1L)).thenReturn(estadisticas);
        when(estadisticaMapper.toDTOList(estadisticas)).thenReturn(Arrays.asList(estadisticaDTO));

        // When
        List<EstadisticaDTO> resultado = estadisticaService.findByEventoId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(estadisticaRepository, times(1)).findByEventoId(1L);
    }

    @Test
    void testDeleteById_CuandoExiste_DebeEliminarla() {
        // Given
        when(estadisticaRepository.existsById(1L)).thenReturn(true);

        // When
        estadisticaService.deleteById(1L);

        // Then
        verify(estadisticaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_CuandoNoExiste_DebeLanzarExcepcion() {
        // Given
        when(estadisticaRepository.existsById(999L)).thenReturn(false);

        // When/Then
        assertThrows(EntityNotFoundException.class, () -> {
            estadisticaService.deleteById(999L);
        });

        verify(estadisticaRepository, never()).deleteById(999L);
    }
}
