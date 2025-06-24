package com.utec.pinfranow.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoActividadCreateDTO {
    private String nombre;
    private String descripcion;
    private LocalDate fecBaja;
    private String razonBaja;
    private String comentario;
    private String estado;
}
