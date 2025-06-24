package com.utec.pinfranow.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioCreateDTO {
    private String nombre;
    private Integer capacidad;
    private BigDecimal precioSocio;
    private BigDecimal precioNoSocio;
    private LocalDate vigenciaPrecios;
    private String observacion;
    private String estado;
}
