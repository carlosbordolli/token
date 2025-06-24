package com.utec.pinfranow.service;

import com.utec.pinfranow.model.Perfil;
import com.utec.pinfranow.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public List<Perfil> findAll() {
        return perfilRepository.findAll();
    }

    public Optional<Perfil> findById(Integer id) {
        return perfilRepository.findById(id);
    }

    public Perfil save(Perfil perfil) {
        // Verifica si se trata de una actualización
        if (perfil.getIdPerfil() != null && perfilRepository.existsById(perfil.getIdPerfil())) {
            Perfil existente = perfilRepository.findById(perfil.getIdPerfil())
                    .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

            // Solo se actualizan los campos editables
            existente.setNomPerfil(perfil.getNomPerfil());
            existente.setDesPerfil(perfil.getDesPerfil());
            existente.setPerEstado(perfil.getPerEstado());

            return perfilRepository.save(existente);
        } else {
            // Es una creación: se guarda directamente
            return perfilRepository.save(perfil);
        }
    }

    public boolean deleteById(Integer id) {
        if (perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Integer id) {
        return perfilRepository.existsById(id);
    }
}
