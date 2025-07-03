package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoActividadCreateDTO {
    @Schema(description = "Nombre del Tipo de Actividad", example = "Baile")
    private String nombre;
    @Schema(description = "Descripción del tipo de actividad", example = "Clase de baile")
    private String descripcion;
    @Schema(description = "Comentarios", example = "Recomendado para mayores de 65")
    private String comentario;
    @Schema(description = "Estado", example = "ACTIVO")
    private String estado;
}
