package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.TipoActividadDTO;
import com.utec.pinfranow.model.Estado;
import com.utec.pinfranow.model.TipoActividad;
import org.springframework.stereotype.Component;

@Component

public class TipoActividadMapper {

    public static TipoActividadDTO toDto(TipoActividad entity) {
        if (entity == null) return null;

        return TipoActividadDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .fecBaja(entity.getFecBaja())
                .razonBaja(entity.getRazonBaja())
                .comentario(entity.getComentario())
                .estado(String.valueOf(entity.getEstado()))
                .build();
    }

    public static TipoActividad toEntity(TipoActividadDTO dto) {
        if (dto == null) return null;

        return TipoActividad.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .fecBaja(dto.getFecBaja())
                .razonBaja(dto.getRazonBaja())
                .comentario(dto.getComentario())
                .estado(Estado.valueOf(dto.getEstado()))
                .build();
    }
}
