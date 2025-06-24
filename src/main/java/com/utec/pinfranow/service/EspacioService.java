package com.utec.pinfranow.service;

import com.utec.pinfranow.model.Espacio;
import com.utec.pinfranow.repository.EspacioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspacioService {

    private final EspacioRepository espacioRepository;

    public List<Espacio> findAll() {
        return espacioRepository.findAll();
    }

    public Optional<Espacio> findById(Integer id) {
        return espacioRepository.findById(id);
    }

    public Espacio save(Espacio espacio) {
        return espacioRepository.save(espacio);
    }

    public boolean deleteById(Integer id) {
        if (espacioRepository.existsById(id)) {
            espacioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Integer id) {
        return espacioRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre) {
        return espacioRepository.existsByNombre(nombre);
    }
}
