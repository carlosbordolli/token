package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.PagoDTO;
import com.utec.pinfranow.model.*;
import com.utec.pinfranow.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private ActividadRepository actividadRepository;

    @Mock
    private ReservaEspacioRepository reservaEspacioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PagoService pagoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Pago pago1 = new Pago();
        pago1.setIdPago(1);
        Pago pago2 = new Pago();
        pago2.setIdPago(2);

        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pago1, pago2));

        List<PagoDTO> result = pagoService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByIdFound() {
        Pago pago = new Pago();
        pago.setIdPago(1);

        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));

        PagoDTO result = pagoService.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getIdPago());
    }

    @Test
    public void testFindByIdNotFound() {
        when(pagoRepository.findById(1)).thenReturn(Optional.empty());

        PagoDTO result = pagoService.findById(1);
        assertNull(result);
    }

    @Test
    public void testSaveSuccess() {
        PagoDTO dto = new PagoDTO();
        dto.setIdUsuario(1);
        dto.setMonto(BigDecimal.valueOf(1000));

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        Pago pagoEntity = new Pago();
        pagoEntity.setIdPago(1);
        pagoEntity.setUsuario(usuario);
        pagoEntity.setMonto(dto.getMonto());

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoEntity);

        PagoDTO result = pagoService.save(dto);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1000), result.getMonto());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    public void testSaveFailsWhenMontoIsZero() {
        PagoDTO dto = new PagoDTO();
        dto.setMonto(BigDecimal.ZERO);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoService.save(dto);
        });

        assertEquals("El monto del pago debe ser mayor a 0", exception.getMessage());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(pagoRepository).deleteById(1);
        pagoService.deleteById(1);
        verify(pagoRepository).deleteById(1);
    }

    @Test
    public void testFindByFecha() {
        LocalDate desde = LocalDate.now().minusDays(5);
        LocalDate hasta = LocalDate.now();

        Pago pago = new Pago();
        pago.setIdPago(1);

        when(pagoRepository.findByFechaCobroBetween(desde, hasta)).thenReturn(List.of(pago));

        List<PagoDTO> result = pagoService.findByFecha(desde, hasta);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindByActividad() {
        Pago pago = new Pago();
        pago.setIdPago(1);

        when(pagoRepository.findByActividadId(10)).thenReturn(List.of(pago));

        List<PagoDTO> result = pagoService.findByActividad(10);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindByUsuarioAndFecha() {
        LocalDate desde = LocalDate.now().minusDays(3);
        LocalDate hasta = LocalDate.now();

        Pago pago = new Pago();
        pago.setIdPago(1);

        when(pagoRepository.findByUsuarioAndFecha(1, desde, hasta)).thenReturn(List.of(pago));

        List<PagoDTO> result = pagoService.findByUsuarioAndFecha(1, desde, hasta);
        assertEquals(1, result.size());
    }
}

