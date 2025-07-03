package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Schema(name = "Espacio DTO", description = "Representa un espacio en la institución")
public class EspacioDTO {

    @Schema(description = "Identificador interno del espacio", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nombre del espacio", example = "Sala de Recreación")
    private String nombre;

    @Schema(description = "Capacidad del espacio", example = "15")
    private Integer capacidad;

    @Schema(description = "Precio del espacio para usuarios socios", example = "500")
    private BigDecimal precioSocio;

    @Schema(description = "Precio del espacio para usuarios no socios", example = "850")
    private BigDecimal precioNoSocio;

    @Schema(description = "Fecha de vigencia del precio actual", example = "2025-12-31")
    private LocalDate vigenciaPrecios;

    @Schema(description = "Observación del espacio", example = "Este espacio es de uso recreativo.")
    private String observacion;

    @Schema(description = "Estado del espacio", example = "ACTIVO")
    private String estado;
}
