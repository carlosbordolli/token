package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Schema(name = "Inscripcion Actividad DTO", description = "Representa la incripción de un usuario a una actividad dentro del sistema")
public class InscripcionActividadDTO {

    @Schema(description = "Identificador interno del usuario", example = "1")
    @NotNull(message = "El id del usuario es obligatorio")
    private Integer idUsuario;

    @Schema(description = "Identificador interno de la acividad", example = "1")
    @NotNull(message = "El id de la actividad es obligatorio")
    private Integer idActividad;

    @Schema(description = "Fecha de incripción de la actividad", example = "31/12/2024", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaInscripcion;

    @Schema(description = "La incripción fue cancelada?", example = "false")
    private Boolean cancelada;
}
