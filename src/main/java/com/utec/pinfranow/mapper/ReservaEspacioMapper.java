package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.ReservaEspacioDTO;
import com.utec.pinfranow.model.*;
import org.springframework.stereotype.Component;

@Component

public class ReservaEspacioMapper {

    public static ReservaEspacioDTO toDto(ReservaEspacio entity) {
        if (entity == null) return null;

        return ReservaEspacioDTO.builder()
                .id(entity.getId())
                .fecReservaActividad(entity.getFecReservaActividad())
                .horaReservaActividad(entity.getHoraReservaActividad())
                .duracion(entity.getDuracion())
                .cantidadPersonas(entity.getCantidadPersonas())
                .fechaVtoSenia(entity.getFechaVtoSenia())
                .costoActividad(entity.getCostoActividad())
                .fecPagoSenia(entity.getFecPagoSenia())
                .importePagado(entity.getImportePagado())
                .importeAPagar(entity.getImporteAPagar())
                .saldoPendiente(entity.getSaldoPendiente())
                .fecConfReserva(entity.getFecConfReserva())
                .horaConfReserva(entity.getHoraConfReserva())
                .resCancelada(entity.getResCancelada())
                .idUsuario(entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null)
                .idEspacio(entity.getEspacio() != null ? entity.getEspacio().getId() : null)
                .build();
    }

    public static ReservaEspacio toEntity(ReservaEspacioDTO dto, Usuario usuario, Espacio espacio) {
        if (dto == null) return null;

        return ReservaEspacio.builder()
                .id(dto.getId())
                .fecReservaActividad(dto.getFecReservaActividad())
                .horaReservaActividad(dto.getHoraReservaActividad())
                .duracion(dto.getDuracion())
                .cantidadPersonas(dto.getCantidadPersonas())
                .costoActividad(dto.getCostoActividad())
                .fecPagoSenia(dto.getFecPagoSenia())
                .importePagado(dto.getImportePagado())
                .importeAPagar(dto.getImporteAPagar())
                .fecConfReserva(dto.getFecConfReserva())
                .horaConfReserva(dto.getHoraConfReserva())
                .resCancelada(dto.getResCancelada() != null ? dto.getResCancelada() : false)
                .usuario(usuario)
                .espacio(espacio)
                .build();
    }
}
