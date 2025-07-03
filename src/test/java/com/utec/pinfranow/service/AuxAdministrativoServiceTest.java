package com.utec.pinfranow.service;

import com.utec.pinfranow.model.AuxAdministrativo;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.repository.AuxAdministrativoRepository;
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

public class AuxAdministrativoServiceTest {

    @Mock
    private AuxAdministrativoRepository auxAdministrativoRepository;

    @InjectMocks
    private AuxAdministrativoService auxAdministrativoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Usuario crearUsuarioEjemplo() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(100);
        usuario.setNroDocumento("12345678");
        return usuario;
    }

    @Test
    public void testFindAll() {
        AuxAdministrativo a1 = new AuxAdministrativo(1, crearUsuarioEjemplo());
        AuxAdministrativo a2 = new AuxAdministrativo(2, crearUsuarioEjemplo());

        when(auxAdministrativoRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<AuxAdministrativo> result = auxAdministrativoService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByIdFound() {
        AuxAdministrativo aux = new AuxAdministrativo(1, crearUsuarioEjemplo());

        when(auxAdministrativoRepository.findById(1)).thenReturn(Optional.of(aux));

        Optional<AuxAdministrativo> result = auxAdministrativoService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(auxAdministrativoRepository.findById(1)).thenReturn(Optional.empty());

        Optional<AuxAdministrativo> result = auxAdministrativoService.findById(1);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        AuxAdministrativo aux = new AuxAdministrativo(null, crearUsuarioEjemplo());

        AuxAdministrativo saved = new AuxAdministrativo(1, aux.getUsuario());
        when(auxAdministrativoRepository.save(aux)).thenReturn(saved);

        AuxAdministrativo result = auxAdministrativoService.save(aux);
        assertEquals(1, result.getId());
        assertEquals("12345678", result.getUsuario().getNroDocumento());
    }

    @Test
    public void testDeleteByIdWhenExists() {
        when(auxAdministrativoRepository.existsById(1)).thenReturn(true);

        boolean result = auxAdministrativoService.deleteById(1);
        verify(auxAdministrativoRepository).deleteById(1);
        assertTrue(result);
    }

    @Test
    public void testDeleteByIdWhenNotExists() {
        when(auxAdministrativoRepository.existsById(1)).thenReturn(false);

        boolean result = auxAdministrativoService.deleteById(1);
        verify(auxAdministrativoRepository, never()).deleteById(anyInt());
        assertFalse(result);
    }
}
