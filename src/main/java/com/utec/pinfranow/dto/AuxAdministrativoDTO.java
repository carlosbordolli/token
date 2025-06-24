package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Schema(name = "Aux Administrativo DTO", description = "Representa a un auxiliar Administrativo dentro del sistema")
public class AuxAdministrativoDTO {

    @Schema(description = "Identificador interno del auxiliar administrativo", example = "1")
    private Integer id;

    @Schema(description = "Identificador interno del usuario", example = "1")
    private Integer idUsuario;
}
