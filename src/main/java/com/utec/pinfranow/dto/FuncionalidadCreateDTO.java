package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionalidadCreateDTO {

    @Schema(description = "Nombre corto de la funcionalidad", example = "LOGIN")
    private String nombre;

    @Schema(description = "Descripción de la funcionalidad", example = "Pantalla de inicio de sesión")
    private String descripcion;

    @Schema(description = "Estado de la funcionalidad", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO"})
    private String estado;
}
