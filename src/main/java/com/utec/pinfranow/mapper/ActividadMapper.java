package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.ActividadDTO;
import com.utec.pinfranow.model.*;
import org.springframework.stereotype.Component;

@Component
public class ActividadMapper {

    public ActividadDTO toDto(Actividad entity) {
        if (entity == null) return null;

        return ActividadDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .objetivo(entity.getObjetivo())
                .fecha(entity.getFecha())
                .hora(entity.getHora())
                .duracion(entity.getDuracion())
                .inscripcion(entity.getInscripcion())
                .costo(entity.getCosto())
                .fecInscripcion(entity.getFecInscripcion())
                .tipoPago(String.valueOf(entity.getTipoPago()))
                .observaciones(entity.getObservaciones())
                .estado(String.valueOf(entity.getEstado()))
                .idTipoActividad(entity.getTipoActividad() != null ? entity.getTipoActividad().getId() : null)
                .idAuxAdministrativo(entity.getAuxAdministrativo() != null ? entity.getAuxAdministrativo().getId() : null)
                .idEspacio(entity.getEspacio() != null ? entity.getEspacio().getId() : null)
                .build();
    }

    public static Actividad toEntity(ActividadDTO dto, TipoActividad tipo, AuxAdministrativo aux, Espacio espacio) {
        if (dto == null) return null;

        return Actividad.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .objetivo(dto.getObjetivo())
                .fecha(dto.getFecha())
                .hora(dto.getHora())
                .duracion(dto.getDuracion())
                .inscripcion(dto.getInscripcion())
                .costo(dto.getCosto())
                .fecInscripcion(dto.getFecInscripcion())
                .tipoPago(TipoPago.valueOf(dto.getTipoPago()))
                .observaciones(dto.getObservaciones())
                .estado(Estado.valueOf(dto.getEstado()))
                .tipoActividad(tipo)
                .auxAdministrativo(aux)
                .espacio(espacio)
                .build();
    }
}
