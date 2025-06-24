package com.utec.pinfranow.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoCreateDTO {
    private LocalDate fechaCobro;
    private BigDecimal monto;
    private String formaCobro;
    private Integer idActividad;      // opcional, puede ser null
    private Integer idReserva;        // opcional, puede ser null
    private Integer idUsuario;
}
