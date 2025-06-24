package com.utec.pinfranow.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEspacioCreateDTO {


    private LocalDate fecReservaActividad;
    private LocalTime horaReservaActividad;
    private LocalTime duracion;

    private Integer cantidadPersonas;

    private LocalDate fechaVtoSenia;

    private BigDecimal costoActividad;
    private LocalDate fecPagoSenia;

    private BigDecimal importePagado;
    private BigDecimal importeAPagar;
    private BigDecimal saldoPendiente;

    private LocalDate fecConfReserva;
    private LocalTime horaConfReserva;

    private Boolean resCancelada;

    private Integer idUsuario;
    private Integer idEspacio;
}
