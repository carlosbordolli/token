package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoActividadDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fecBaja;
    private String razonBaja;
    private String comentario;
    private String estado;
}
