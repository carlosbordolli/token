package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.AccesoFuncionalidadCreateDTO;
import com.utec.pinfranow.dto.AccesoFuncionalidadDTO;
import com.utec.pinfranow.mapper.AccesoFuncionalidadMapper;
import com.utec.pinfranow.model.AccesoFuncionalidad;
import com.utec.pinfranow.model.Funcionalidad;
import com.utec.pinfranow.model.Perfil;
import com.utec.pinfranow.model.ids.AccesoFuncionalidadId;
import com.utec.pinfranow.repository.AccesoFuncionalidadRepository;
import com.utec.pinfranow.repository.FuncionalidadRepository;
import com.utec.pinfranow.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccesoFuncionalidadService {

    private final AccesoFuncionalidadRepository repository;
    private final PerfilRepository perfilRepository;
    private final FuncionalidadRepository funcionalidadRepository;
    private final AccesoFuncionalidadMapper mapper;

    public List<AccesoFuncionalidadDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<AccesoFuncionalidadDTO> findByPerfil(Integer idPerfil) {
        return repository.findByPerfilIdPerfil(idPerfil).stream()
                .map(mapper::toDto)
                .toList();
    }

    public Optional<AccesoFuncionalidadDTO> findById(AccesoFuncionalidadId id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public AccesoFuncionalidadDTO save(AccesoFuncionalidadCreateDTO dto) {
        Perfil perfil = perfilRepository.findById(dto.getIdPerfil())
                .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));
        Funcionalidad funcionalidad = funcionalidadRepository.findById(dto.getIdFuncionalidad())
                .orElseThrow(() -> new IllegalArgumentException("Funcionalidad no encontrada"));

        AccesoFuncionalidad entity = mapper.toEntity(dto, perfil, funcionalidad);
        AccesoFuncionalidad saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    public boolean deleteById(AccesoFuncionalidadId id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
