package com.utec.pinfranow.service;

import com.utec.pinfranow.model.Perfil;
import com.utec.pinfranow.repository.PerfilRepository;
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

public class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @InjectMocks
    private PerfilService perfilService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Perfil perfil = new Perfil();
        perfil.setIdPerfil(1);
        perfil.setNomPerfil("ADMIN");
        when(perfilRepository.findAll()).thenReturn(Arrays.asList(perfil));

        List<Perfil> perfiles = perfilService.findAll();
        assertEquals(1, perfiles.size());
        assertEquals("ADMIN", perfiles.get(0).getNomPerfil());
    }

    @Test
    public void testFindById() {
        Perfil perfil = new Perfil();
        perfil.setIdPerfil(1);
        perfil.setNomPerfil("USER");
        when(perfilRepository.findById(1)).thenReturn(Optional.of(perfil));

        Optional<Perfil> result = perfilService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("USER", result.get().getNomPerfil());
    }

    @Test
    public void testSaveNewPerfil() {
        Perfil perfil = new Perfil();
        perfil.setNomPerfil("NEW");
        when(perfilRepository.save(perfil)).thenReturn(perfil);

        Perfil saved = perfilService.save(perfil);
        assertEquals("NEW", saved.getNomPerfil());
    }

    @Test
    public void testDeleteByIdExists() {
        when(perfilRepository.existsById(1)).thenReturn(true);
        doNothing().when(perfilRepository).deleteById(1);

        boolean deleted = perfilService.deleteById(1);
        assertTrue(deleted);
        verify(perfilRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteByIdNotExists() {
        when(perfilRepository.existsById(2)).thenReturn(false);

        boolean deleted = perfilService.deleteById(2);
        assertFalse(deleted);
        verify(perfilRepository, never()).deleteById(2);
    }
}