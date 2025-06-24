package com.utec.pinfranow.service;

import com.utec.pinfranow.model.AuxAdministrativo;
import com.utec.pinfranow.repository.AuxAdministrativoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuxAdministrativoService {

    private final AuxAdministrativoRepository auxRepository;

    public List<AuxAdministrativo> findAll() {
        return auxRepository.findAll();
    }

    public Optional<AuxAdministrativo> findById(Integer id) {
        return auxRepository.findById(id);
    }

    public Optional<AuxAdministrativo> findByUsuarioId(Integer usuarioId) {
        return auxRepository.findByUsuarioIdUsuario(usuarioId);
    }

    public AuxAdministrativo save(AuxAdministrativo aux) {
        return auxRepository.save(aux);
    }

    public boolean deleteById(Integer id) {
        if (auxRepository.existsById(id)) {
            auxRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsByUsuarioId(Integer idUsuario) {
        return auxRepository.existsByUsuarioIdUsuario(idUsuario);
    }
}
