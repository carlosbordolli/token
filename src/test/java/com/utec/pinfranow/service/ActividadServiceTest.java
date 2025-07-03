package com.utec.pinfranow.service;

import com.utec.pinfranow.model.*;
import com.utec.pinfranow.repository.ActividadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActividadServiceTest {

    @Mock
    private ActividadRepository actividadRepository;

    @InjectMocks
    private ActividadService actividadService;

    private Actividad actividad;

    @BeforeEach
    void setUp() {
        TipoActividad tipoActividad = new TipoActividad();
        AuxAdministrativo auxAdministrativo = new AuxAdministrativo();
        Espacio espacio = new Espacio();
        actividad = Actividad.builder()
                .id(1)
                .nombre("Fisioterapia")
                .descripcion("Mejorar lesiones")
                .fecha(LocalDate.of(2025, 6, 26))
                .hora(LocalTime.of(10, 0))
                .duracion(LocalTime.of(1, 0))
                .inscripcion(true)
                .costo(500)
                .fecInscripcion(LocalDate.of(2025, 6, 26))
                .tipoPago(TipoPago.EFECTIVO)
                .observaciones("Observación de mejoría")
                .estado(Estado.ACTIVO)
                .tipoActividad(tipoActividad)
                .auxAdministrativo(auxAdministrativo)
                .espacio(espacio)
                .build();
    }

    @Test
    void testSaveActividad() {
        Actividad actividad = Actividad.builder()
                .nombre("Prueba")
                .fecha(LocalDate.now().plusDays(1))
                .hora(LocalTime.of(10, 0))
                .duracion(LocalTime.of(0, 30))
                .inscripcion(true)
                .costo(500)
                .fecInscripcion(LocalDate.now())
                .espacio(Espacio.builder().id(1).build())
                .build();


        when(actividadRepository.save(any(Actividad.class))).thenReturn(actividad);

        // Ejecución del servicio
        Actividad guardada = actividadService.save(actividad);

        // Verificación
        assertEquals("Prueba", guardada.getNombre());
        verify(actividadRepository).save(actividad);
    }

    @Test
    void testListarTodasLasActividades() {
        List<Actividad> actividades = Arrays.asList(actividad);
        when(actividadRepository.findAll()).thenReturn(actividades);

        List<Actividad> resultado = actividadService.findAll();

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Fisioterapia", resultado.get(0).getNombre());
        verify(actividadRepository, times(1)).findAll();
    }
    @Test
    void testListarActividadPorId() {
        when(actividadRepository.findById(1)).thenReturn(Optional.of(actividad));

        Optional<Actividad> resultado = actividadService.findById(1);

        // Verificaciones
        assertTrue(resultado.isPresent());
        assertEquals("Fisioterapia", resultado.get().getNombre());
        verify(actividadRepository, times(1)).findById(1);
    }
}

