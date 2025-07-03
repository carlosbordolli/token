package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccesoFuncionalidadDTO {
    @Schema(description = "idPerfil", example = "1")
    private Integer idPerfil;
    @Schema(description = "idFuncionalidad", example = "1")
    private Integer idFuncionalidad;
}
