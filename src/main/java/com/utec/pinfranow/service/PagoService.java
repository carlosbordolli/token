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
    private final ReservaEspacioRepository reservaEspacioRepository;
    private final UsuarioRepository usuarioRepository;
    private final InscripcionActividadRepository inscripcionActividadRepository;

    public PagoDTO save(PagoDTO dto) {
        Actividad actividad = null;
        if (dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a 0");
        }

        if (dto.getFechaCobro() == null) {
            throw new IllegalArgumentException("La fecha de cobro no puede estar vacía");
        }

        if (dto.getFechaCobro().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de cobro no puede ser futura");
        }

        if (dto.getIdActividad() != null) {
            actividad = actividadRepository.findById(dto.getIdActividad())
                    .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));


            if (!inscripcionActividadRepository.existsById_ActividadIdAndId_UsuarioId(dto.getIdActividad(), dto.getIdUsuario())) {
                throw new IllegalArgumentException("El usuario no está inscripto en la actividad");
            }

            }

            ReservaEspacio reserva = null;
            if (dto.getIdReserva() != null) {
                reserva = reservaEspacioRepository.findById(dto.getIdReserva())
                        .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

                if (!reserva.getUsuario().getIdUsuario().equals(dto.getIdUsuario())) {
                    throw new IllegalArgumentException("El usuario no tiene una reserva registrada");
                }

                if (!Boolean.TRUE.equals(reserva.getFecConfReserva() != null)) {
                    throw new IllegalArgumentException("La reserva no está confirmada");
                }

                if (dto.getMonto().compareTo(reserva.getImporteAPagar()) > 0) {
                    throw new IllegalArgumentException("El monto no puede superar el importe total de la reserva");
                }

                // Validar fecha de vencimiento de seña
                LocalDate vencimiento = reserva.getFecReservaActividad().plusDays(7); // 5 días hábiles
                if (dto.getFechaCobro().isAfter(vencimiento)) {
                    throw new IllegalArgumentException("La fecha de cobro no puede ser posterior a la fecha de vencimiento de la seña");
                }
            }


            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + dto.getIdUsuario()));

            Pago pago = PagoMapper.toEntity(dto, actividad, reserva, usuario);

            pago = pagoRepository.save(pago);

            return PagoMapper.toDto(pago);
        }

        public List<PagoDTO> findAll () {
            return pagoRepository.findAll().stream()
                    .map(PagoMapper::toDto)
                    .collect(Collectors.toList());
        }

        public PagoDTO findById (Integer idPago){
            return pagoRepository.findById(idPago)
                    .map(PagoMapper::toDto)
                    .orElse(null);
        }

        public void deleteById (Integer idPago){
            pagoRepository.deleteById(idPago);
        }

        public List<PagoDTO> findByFecha (LocalDate desde, LocalDate hasta){
            return pagoRepository.findByFechaCobroBetween(desde, hasta).stream()
                    .map(PagoMapper::toDto)
                    .collect(Collectors.toList());
        }

        public List<PagoDTO> findByActividad (Integer idActividad){
            return pagoRepository.findByActividadId(idActividad).stream()
                    .map(PagoMapper::toDto)
                    .collect(Collectors.toList());
        }

        public List<PagoDTO> findByUsuarioAndFecha (Integer idUsuario, LocalDate desde, LocalDate hasta){
            return pagoRepository.findByUsuarioAndFecha(idUsuario, desde, hasta).stream()
                    .map(PagoMapper::toDto)
                    .collect(Collectors.toList());
        }
    }