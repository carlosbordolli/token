package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEspacioDTO {

    @Schema(description = "Identificador interno de la reserva de espacio", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Fecha de reserva de la actividad", example = "2025-07-29")
    private LocalDate fecReservaActividad;

    @Schema(description = "Hora de reserva de la actividad", example = "19:00:00")
    private LocalTime horaReservaActividad;

    @Schema(description = "Duración de la actividad", example = "01:00:00")
    private LocalTime duracion;

    @Schema(description = "Cantidad de participantes", example = "15")
    private Integer cantidadPersonas;

    @Schema(description = "Fecha de vencimiento de la seña", example = "2025-02-31", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate fechaVtoSenia;

    @Schema(description = "Costo de la actividad", example = "500")
    private BigDecimal costoActividad;

    @Schema(description = "Fecha pago de la seña", example = "2025-07-10")
    private LocalDate fecPagoSenia;

    @Schema(description = "Importe pagado", example = "250")
    private BigDecimal importePagado;

    @Schema(description = "Importe a pagar", example = "250")
    private BigDecimal importeAPagar;

    @Schema(description = "Saldo pendiente", example = "250")
    private BigDecimal saldoPendiente;

    @Schema(description = "Fecha confirmada de reserva", example = "2025-07-10")
    private LocalDate fecConfReserva;

    @Schema(description = "Hora confirmada de reserva de la actividad", example = "19:00:00")
    private LocalTime horaConfReserva;

    @Schema(description = "Reserva cancelada?", example = "false")
    private Boolean resCancelada;

    @Schema(description = "Identificador interno de usuario", example = "1")
    private Integer idUsuario;

    @Schema(description = "Identificador interno de espacio", example = "1")
    private Integer idEspacio;
}
