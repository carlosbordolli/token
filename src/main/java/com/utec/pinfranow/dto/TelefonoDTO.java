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
@Schema(name = "Telefono DTO", description = "Representa un número de teléfono del usuario")
public class TelefonoDTO {

    @Schema(description = "Identificador del teléfono", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idTelefono;

    @Schema(description = "Número de teléfono", example = "099123456")
    private String numero;
}
