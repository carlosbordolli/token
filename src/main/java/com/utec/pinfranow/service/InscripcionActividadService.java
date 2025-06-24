package com.utec.pinfranow.service;

import com.utec.pinfranow.model.ids.InscripcionActividadId;
import com.utec.pinfranow.model.InscripcionActividad;
import com.utec.pinfranow.repository.InscripcionActividadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscripcionActividadService {

    private final InscripcionActividadRepository inscripcionActividadRepository;

    public List<InscripcionActividad> findAll() {
        return inscripcionActividadRepository.findAll();
    }

    public Optional<InscripcionActividad> findById(InscripcionActividadId id) {
        return inscripcionActividadRepository.findById(id);
    }

    public InscripcionActividad save(InscripcionActividad inscripcionActividad) {
        return inscripcionActividadRepository.save(inscripcionActividad);
    }

    public boolean deleteById(InscripcionActividadId id) {
        if (inscripcionActividadRepository.existsById(id)) {
            inscripcionActividadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(InscripcionActividadId id) {
        return inscripcionActividadRepository.existsById(id);
    }
}
