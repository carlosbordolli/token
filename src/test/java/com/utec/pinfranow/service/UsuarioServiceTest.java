package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.TelefonoDTO;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Usuario usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        Usuario usuario2 = new Usuario();
        usuario2.setIdUsuario(2);

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> result = usuarioService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByIdFound() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getIdUsuario());
    }

    @Test
    public void testFindByIdNotFound() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.findById(1);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNroDocumento("12345678");
        usuario.setFecNacimiento(LocalDate.of(2000, 1, 1));

        List<TelefonoDTO> telefonos = List.of(); 

        when(usuarioRepository.existsById(1)).thenReturn(false); // simula que es nuevo
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.save(usuario, telefonos);

        assertEquals(1, result.getIdUsuario());
    }

    @Test
    public void testDeleteByIdWhenExists() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        boolean result = usuarioService.deleteById(1);
        verify(usuarioRepository).deleteById(1);
        assertTrue(result);
    }

    @Test
    public void testDeleteByIdWhenNotExists() {
        when(usuarioRepository.existsById(1)).thenReturn(false);

        boolean result = usuarioService.deleteById(1);
        verify(usuarioRepository, never()).deleteById(anyInt());
        assertFalse(result);
    }
}

