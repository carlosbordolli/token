package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.FuncionalidadCreateDTO;
import com.utec.pinfranow.dto.FuncionalidadDTO;
import com.utec.pinfranow.mapper.FuncionalidadMapper;
import com.utec.pinfranow.model.Funcionalidad;
import com.utec.pinfranow.repository.FuncionalidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionalidadService {

    private final FuncionalidadRepository funcionalidadRepository;
    private final FuncionalidadMapper funcionalidadMapper;

    public List<FuncionalidadDTO> findAll() {
        return funcionalidadRepository.findAll()
                .stream()
                .map(funcionalidadMapper::toDto)
                .collect(Collectors.toList());
    }

    public FuncionalidadDTO findById(Integer id) {
        Optional<Funcionalidad> opt = funcionalidadRepository.findById(id);
        return opt.map(funcionalidadMapper::toDto).orElse(null);
    }

    public FuncionalidadDTO save(FuncionalidadCreateDTO dto) {
        Funcionalidad entity = funcionalidadMapper.toEntity(dto);
        entity = funcionalidadRepository.save(entity);
        return funcionalidadMapper.toDto(entity);
    }

    public FuncionalidadDTO update(Integer id, FuncionalidadCreateDTO dto) {
        Optional<Funcionalidad> opt = funcionalidadRepository.findById(id);
        if (opt.isPresent()) {
            Funcionalidad entity = opt.get();
            entity.setNombre(dto.getNombre());
            entity.setDescripcion(dto.getDescripcion());
            entity.setEstado(dto.getEstado());
            return funcionalidadMapper.toDto(funcionalidadRepository.save(entity));
        }
        return null;
    }

    public Optional<Funcionalidad> findEntityById(Integer id) {
        return funcionalidadRepository.findById(id);
    }

    public boolean deleteById(Integer id) {
        if (funcionalidadRepository.existsById(id)) {
            funcionalidadRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
