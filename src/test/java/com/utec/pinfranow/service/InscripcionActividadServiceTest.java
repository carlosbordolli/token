package com.utec.pinfranow.service;

import com.utec.pinfranow.model.*;
import com.utec.pinfranow.model.ids.InscripcionActividadId;
import com.utec.pinfranow.repository.InscripcionActividadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InscripcionActividadServiceTest {

    @Mock
    private InscripcionActividadRepository inscripcionActividadRepository;

    @InjectMocks
    private InscripcionActividadService inscripcionActividadService;

    private InscripcionActividadId sampleId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleId = new InscripcionActividadId(10, 1); // actividadId, usuarioId
    }

    @Test
    public void testFindAll() {
        InscripcionActividad i1 = new InscripcionActividad();
        i1.setId(new InscripcionActividadId(10, 1));

        InscripcionActividad i2 = new InscripcionActividad();
        i2.setId(new InscripcionActividadId(20, 2));

        when(inscripcionActividadRepository.findAll()).thenReturn(Arrays.asList(i1, i2));

        List<InscripcionActividad> result = inscripcionActividadService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByIdFound() {
        InscripcionActividad inscripcion = new InscripcionActividad();
        inscripcion.setId(sampleId);

        when(inscripcionActividadRepository.findById(sampleId)).thenReturn(Optional.of(inscripcion));

        Optional<InscripcionActividad> result = inscripcionActividadService.findById(sampleId);
        assertTrue(result.isPresent());
        assertEquals(sampleId, result.get().getId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(inscripcionActividadRepository.findById(sampleId)).thenReturn(Optional.empty());

        Optional<InscripcionActividad> result = inscripcionActividadService.findById(sampleId);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        Actividad actividad = new Actividad();
        actividad.setId(10);

        InscripcionActividad inscripcion = InscripcionActividad.builder()
                .id(sampleId)
                .usuario(usuario)
                .actividad(actividad)
                .fechaInscripcion(LocalDateTime.now())
                .cancelada(false)
                .build();

        when(inscripcionActividadRepository.save(inscripcion)).thenReturn(inscripcion);

        InscripcionActividad result = inscripcionActividadService.save(inscripcion);
        assertEquals(sampleId, result.getId());
        assertEquals(usuario.getIdUsuario(), result.getUsuario().getIdUsuario());
    }

    @Test
    public void testDeleteByIdWhenExists() {
        when(inscripcionActividadRepository.existsById(sampleId)).thenReturn(true);

        boolean result = inscripcionActividadService.deleteById(sampleId);
        verify(inscripcionActividadRepository).deleteById(sampleId);
        assertTrue(result);
    }

    @Test
    public void testDeleteByIdWhenNotExists() {
        when(inscripcionActividadRepository.existsById(sampleId)).thenReturn(false);

        boolean result = inscripcionActividadService.deleteById(sampleId);
        verify(inscripcionActividadRepository, never()).deleteById(any());
        assertFalse(result);
    }
}

