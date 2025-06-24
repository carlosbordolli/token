package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.PagoDTO;
import com.utec.pinfranow.model.*;

public class PagoMapper {

    public static PagoDTO toDto(Pago entity) {
        if (entity == null) return null;

        return PagoDTO.builder()
                .idPago(entity.getIdPago())
                .fechaCobro(entity.getFechaCobro())
                .monto(entity.getMonto())
                .formaCobro(entity.getFormaCobro())
                .idActividad(entity.getActividad() != null ? (entity.getActividad().getId() != null ? entity.getActividad().getId().intValue() : null) : null)
                .idReserva(entity.getReserva() != null ? entity.getReserva().getId() : null)
                .idUsuario(entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null)
                .build();
    }

    public static Pago toEntity(PagoDTO dto, Actividad actividad, ReservaEspacio reserva, Usuario usuario) {
        if (dto == null) return null;

        return Pago.builder()
                .idPago(dto.getIdPago())
                .fechaCobro(dto.getFechaCobro())
                .monto(dto.getMonto())
                .formaCobro(dto.getFormaCobro())
                .actividad(actividad)
                .reserva(reserva)
                .usuario(usuario)
                .build();
    }
}
