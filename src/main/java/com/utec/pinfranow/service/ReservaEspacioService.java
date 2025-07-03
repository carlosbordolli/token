package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.ReservaEspacioCreateDTO;
import com.utec.pinfranow.dto.ReservaEspacioDTO;
import com.utec.pinfranow.mapper.ReservaEspacioMapper;
import com.utec.pinfranow.model.Espacio;
import com.utec.pinfranow.model.ReservaEspacio;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.repository.EspacioRepository;
import com.utec.pinfranow.repository.ReservaEspacioRepository;
import com.utec.pinfranow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaEspacioService {

    private final ReservaEspacioRepository reservaEspacioRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspacioRepository espacioRepository;

    public List<ReservaEspacio> findAll() {
        return reservaEspacioRepository.findAll();
    }

    public Optional<ReservaEspacio> findById(Integer id) {
        return reservaEspacioRepository.findById(id);
    }

    public ReservaEspacio save(ReservaEspacio reserva) {
        return reservaEspacioRepository.save(reserva);
    }

    public boolean deleteById(Integer id) {
        if (reservaEspacioRepository.existsById(id)) {
            reservaEspacioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Integer id) {
        return reservaEspacioRepository.existsById(id);
    }

    public List<ReservaEspacio> findByEspacioIdAndFecha(Integer idEspacio, java.time.LocalDate fecha) {
        return reservaEspacioRepository.findByEspacioIdAndFecReservaActividad(idEspacio, fecha);
    }
    public ReservaEspacioDTO create(ReservaEspacioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Espacio espacio = espacioRepository.findById(dto.getIdEspacio())
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));

        if (dto.getFecReservaActividad() != null && dto.getFecConfReserva() != null) {
            if (dto.getFecReservaActividad().isAfter(dto.getFecConfReserva())) {
                throw new IllegalArgumentException("La fecha de la reserva no puede ser posterior a la fecha de confirmación.");
            }
        }

        ReservaEspacio reserva = ReservaEspacioMapper.toEntity(dto, usuario, espacio);
        reserva = reservaEspacioRepository.save(reserva);

        return ReservaEspacioMapper.toDto(reserva);
    }
    public boolean cancelarReserva(Integer id) {
        Optional<ReservaEspacio> reservaOpt = reservaEspacioRepository.findById(id);
        if (reservaOpt.isPresent()) {
            ReservaEspacio reserva = reservaOpt.get();
            reserva.setResCancelada(true);
            reservaEspacioRepository.save(reserva);
            return true;
        }
        return false;
    }

    public List<ReservaEspacioDTO> findByFecha(LocalDate desde, LocalDate hasta) {
        return reservaEspacioRepository.findByFecReservaActividadBetween(desde, hasta).stream()
                .map(ReservaEspacioMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaEspacioDTO> findByEspacio(Integer idEspacio) {
        return reservaEspacioRepository.findByEspacioId(idEspacio).stream()
                .map(ReservaEspacioMapper::toDto)
                .collect(Collectors.toList());
    }
}
