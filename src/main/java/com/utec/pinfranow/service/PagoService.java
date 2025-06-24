package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.PagoDTO;
import com.utec.pinfranow.mapper.PagoMapper;
import com.utec.pinfranow.model.*;
import com.utec.pinfranow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.math.BigDecimal;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final ActividadRepository actividadRepository;
    private final ReservaEspacioRepository reservaEspaciosRepository;
    private final UsuarioRepository usuarioRepository;

    public PagoDTO save(PagoDTO dto) {
        Actividad actividad = null;
        if (dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a 0");
        }

        if (dto.getIdActividad() != null) {
            actividad = actividadRepository.findById(dto.getIdActividad()).orElse(null);
        }
        ReservaEspacio reserva = null;
        if (dto.getIdReserva() != null) {
            reserva = reservaEspaciosRepository.findById(dto.getIdReserva()).orElse(null);
        }
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + dto.getIdUsuario()));

        Pago pago = PagoMapper.toEntity(dto, actividad, reserva, usuario);

        pago = pagoRepository.save(pago);

        return PagoMapper.toDto(pago);
    }

    public List<PagoDTO> findAll() {
        return pagoRepository.findAll().stream()
                .map(PagoMapper::toDto)
                .collect(Collectors.toList());
    }

    public PagoDTO findById(Integer idPago) {
        return pagoRepository.findById(idPago)
                .map(PagoMapper::toDto)
                .orElse(null);
    }

    public void deleteById(Integer idPago) {
        pagoRepository.deleteById(idPago);
    }

    public List<PagoDTO> findByFecha(LocalDate desde, LocalDate hasta) {
        return pagoRepository.findByFechaCobroBetween(desde, hasta).stream()
                .map(PagoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PagoDTO> findByActividad(Integer idActividad) {
        return pagoRepository.findByActividadId(idActividad).stream()
                .map(PagoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PagoDTO> findByUsuarioAndFecha(Integer idUsuario, LocalDate desde, LocalDate hasta) {
        return pagoRepository.findByUsuarioAndFecha(idUsuario, desde, hasta).stream()
                .map(PagoMapper::toDto)
                .collect(Collectors.toList());
    }
}
