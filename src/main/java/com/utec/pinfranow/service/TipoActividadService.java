package com.utec.pinfranow.service;

import com.utec.pinfranow.model.TipoActividad;
import com.utec.pinfranow.repository.TipoActividadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoActividadService {

    private final TipoActividadRepository repository;

    public List<TipoActividad> findAll() {
        return repository.findAll();
    }

    public Optional<TipoActividad> findById(Integer id) {
        return repository.findById(id);
    }

    public TipoActividad save(TipoActividad tipoActividad) {
        return repository.save(tipoActividad);
    }

    public boolean deleteById(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsByNombre(String nombre) {
        return repository.existsByNombre(nombre);
    }
}
