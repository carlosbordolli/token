package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionalidadDTO {

    @Schema(description = "ID de la funcionalidad", example = "1")
    private Integer id;

    @Schema(description = "Nombre corto de la funcionalidad", example = "LOGIN")
    private String nombre;

    @Schema(description = "Descripción de la funcionalidad", example = "Pantalla de inicio de sesión")
    private String descripcion;

    @Schema(description = "Estado de la funcionalidad", example = "ACTIVO")
    private String estado;
}