package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.EspacioDTO;
import com.utec.pinfranow.model.Espacio;
import com.utec.pinfranow.model.Estado;
import org.springframework.stereotype.Component;

@Component
public class EspacioMapper {

    public EspacioDTO toDto(Espacio entity) {
        if (entity == null) return null;

        return EspacioDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .capacidad(entity.getCapacidad())
                .precioSocio(entity.getPrecioSocio())
                .precioNoSocio(entity.getPrecioNoSocio())
                .vigenciaPrecios(entity.getVigenciaPrecios())
                .observacion(entity.getObservacion())
                .estado(String.valueOf(entity.getEstado()))
                .build();
    }

    public static Espacio toEntity(EspacioDTO dto) {
        if (dto == null) return null;

        return Espacio.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .capacidad(dto.getCapacidad())
                .precioSocio(dto.getPrecioSocio())
                .precioNoSocio(dto.getPrecioNoSocio())
                .vigenciaPrecios(dto.getVigenciaPrecios())
                .observacion(dto.getObservacion())
                .estado(Estado.valueOf(dto.getEstado()))
                .build();
    }
}
