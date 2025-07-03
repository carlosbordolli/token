package com.utec.pinfranow.service;

import com.utec.pinfranow.model.TipoActividad;
import com.utec.pinfranow.repository.TipoActividadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TipoActividadServiceTest {

    @Mock
    private TipoActividadRepository tipoActividadRepository;

    @InjectMocks
    private TipoActividadService tipoActividadService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        TipoActividad t1 = new TipoActividad();
        t1.setId(1);
        TipoActividad t2 = new TipoActividad();
        t2.setId(2);

        when(tipoActividadRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<TipoActividad> result = tipoActividadService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByIdFound() {
        TipoActividad tipo = new TipoActividad();
        tipo.setId(1);

        when(tipoActividadRepository.findById(1)).thenReturn(Optional.of(tipo));

        Optional<TipoActividad> result = tipoActividadService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(tipoActividadRepository.findById(1)).thenReturn(Optional.empty());

        Optional<TipoActividad> result = tipoActividadService.findById(1);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        TipoActividad tipo = new TipoActividad();
        tipo.setId(1);
        tipo.setNombre("Deportivo");

        when(tipoActividadRepository.save(tipo)).thenReturn(tipo);

        TipoActividad result = tipoActividadService.save(tipo);
        assertEquals("Deportivo", result.getNombre());
    }

    @Test
    public void testDeleteByIdWhenExists() {
        when(tipoActividadRepository.existsById(1)).thenReturn(true);

        boolean result = tipoActividadService.deleteById(1);
        verify(tipoActividadRepository).deleteById(1);
        assertTrue(result);
    }

    @Test
    public void testDeleteByIdWhenNotExists() {
        when(tipoActividadRepository.existsById(1)).thenReturn(false);

        boolean result = tipoActividadService.deleteById(1);
        verify(tipoActividadRepository, never()).deleteById(anyInt());
        assertFalse(result);
    }
}
