package com.utec.pinfranow.service;

import com.utec.pinfranow.model.Actividad;
import com.utec.pinfranow.repository.ActividadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActividadService {

    private final ActividadRepository actividadRepository;

    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }

    public Optional<Actividad> findById(Integer id) {
        return actividadRepository.findById(id);
    }

    public Actividad save(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    public boolean deleteById(Integer id) {
        if (actividadRepository.existsById(id)) {
            actividadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Integer id) {
        return actividadRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre) {
        return actividadRepository.existsByNombre(nombre);
    }

    // Validar si hay solapamiento de actividades en el mismo espacio y hora
    public boolean haySolapamiento(Integer idEspacio, LocalDate fecha, LocalTime hora) {
        return !actividadRepository.findByEspacioIdAndFechaAndHora(idEspacio, fecha, hora).isEmpty();
    }
}
