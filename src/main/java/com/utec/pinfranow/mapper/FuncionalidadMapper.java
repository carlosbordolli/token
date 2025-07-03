package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.FuncionalidadDTO;
import com.utec.pinfranow.dto.FuncionalidadCreateDTO;
import com.utec.pinfranow.model.Funcionalidad;
import org.springframework.stereotype.Component;

@Component
public class FuncionalidadMapper {

    public FuncionalidadDTO toDto(Funcionalidad entity) {
        if (entity == null) return null;

        return FuncionalidadDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .build();
    }

    public Funcionalidad toEntity(FuncionalidadCreateDTO dto) {
        if (dto == null) return null;

        return Funcionalidad.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .estado(dto.getEstado())
                .build();
    }
}
