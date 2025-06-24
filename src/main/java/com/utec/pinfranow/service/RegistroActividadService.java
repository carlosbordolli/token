package com.utec.pinfranow.service;

import com.utec.pinfranow.model.RegistroActividad;
import com.utec.pinfranow.repository.RegistroActividadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistroActividadService {

    private final RegistroActividadRepository repository;

    public void registrar(String usuario, String metodo, String endpoint) {
        RegistroActividad registro = RegistroActividad.builder()
                .usuario(usuario)
                .metodo(metodo)
                .endpoint(endpoint)
                .fechaHora(LocalDateTime.now())
                .build();

        repository.save(registro);
    }
}
