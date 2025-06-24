package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.InscripcionActividadDTO;
import com.utec.pinfranow.model.InscripcionActividad;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.model.Actividad;
import com.utec.pinfranow.model.ids.InscripcionActividadId;
import org.springframework.stereotype.Component;

@Component

public class InscripcionActividadMapper {

    public InscripcionActividadDTO toDto(InscripcionActividad inscripcion) {
        if (inscripcion == null) return null;
        return InscripcionActividadDTO.builder()
                .idUsuario(inscripcion.getUsuario() != null ? inscripcion.getUsuario().getIdUsuario().intValue() : null)
                .idActividad(inscripcion.getActividad() != null ? inscripcion.getActividad().getId().intValue() : null)
                .fechaInscripcion(inscripcion.getFechaInscripcion())
                .cancelada(inscripcion.getCancelada())
                .build();
    }

    public static InscripcionActividad toEntity(
            InscripcionActividadDTO dto, Usuario usuario, Actividad actividad) {
        if (dto == null) return null;
        InscripcionActividad insc = InscripcionActividad.builder()
                .usuario(usuario)
                .actividad(actividad)
                .fechaInscripcion(dto.getFechaInscripcion())
                .cancelada(dto.getCancelada() != null ? dto.getCancelada() : false)
                .build();

        insc.setId(new InscripcionActividadId(
                dto.getIdUsuario(),
                dto.getIdActividad()
        ));
        return insc;
    }
}
