package com.utec.pinfranow.service;

import com.utec.pinfranow.model.ReservaEspacio;
import com.utec.pinfranow.repository.ReservaEspacioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaEspacioService {

    private final ReservaEspacioRepository reservaEspacioRepository;

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
}
