package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Schema(name = "Pago DTO", description = "Representa un pago dentro del sistema")
public class PagoDTO {

    @Schema(description = "Identificador interno del pago", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idPago;

    @Schema(description = "Fecha de cobro", example = "2025-06-29")
    private LocalDate fechaCobro;

    @Schema(description = "Monto del pago", example = "250")
    private BigDecimal monto;

    @Schema(description = "Forma de cobro", example = "EFECTIVO")
    private String formaCobro;

    @Schema(description = "Identificador interno de la actividad", example = "1")
    private Integer idActividad;

    @Schema(description = "Identificador interno de la reserva", example = "1")
    private Integer idReserva;

    @Schema(description = "Identificador interno del usuario que realiza el pago", example = "1")
    private Integer idUsuario;
}
