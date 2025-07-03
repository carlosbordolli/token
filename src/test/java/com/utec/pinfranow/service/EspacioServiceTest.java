package com.utec.pinfranow.service;

import com.utec.pinfranow.model.Espacio;
import com.utec.pinfranow.repository.EspacioRepository;
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

public class EspacioServiceTest {

    @Mock
    private EspacioRepository espacioRepository;

    @InjectMocks
    private EspacioService espacioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Espacio e1 = new Espacio();
        e1.setId(1);
        Espacio e2 = new Espacio();
        e2.setId(2);

        when(espacioRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Espacio> result = espacioService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByIdFound() {
        Espacio espacio = new Espacio();
        espacio.setId(1);

        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));

        Optional<Espacio> result = espacioService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(espacioRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Espacio> result = espacioService.findById(1);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        Espacio espacio = new Espacio();
        espacio.setId(1);
        espacio.setNombre("Biblioteca");

        when(espacioRepository.save(espacio)).thenReturn(espacio);

        Espacio result = espacioService.save(espacio);
        assertEquals("Biblioteca", result.getNombre());
    }

    @Test
    public void testDeleteByIdWhenExists() {
        when(espacioRepository.existsById(1)).thenReturn(true);

        boolean result = espacioService.deleteById(1);
        verify(espacioRepository).deleteById(1);
        assertTrue(result);
    }

    @Test
    public void testDeleteByIdWhenNotExists() {
        when(espacioRepository.existsById(1)).thenReturn(false);

        boolean result = espacioService.deleteById(1);
        verify(espacioRepository, never()).deleteById(anyInt());
        assertFalse(result);
    }
}
