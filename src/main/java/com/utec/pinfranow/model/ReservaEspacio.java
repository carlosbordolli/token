package com.utec.pinfranow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva_espacios")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservaEspacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer id;

    private LocalDate fecReservaActividad;
    private LocalTime horaReservaActividad;
    private LocalTime duracion;

    @Positive
    private Integer cantidadPersonas;

    /* Campos calculados en la BD */
    @Column(name = "fec_vto_sena", insertable = false, updatable = false)
    private LocalDate fechaVtoSenia;

    @Column(name = "cst_actividad", precision = 10, scale = 2)
    @Digits(integer = 8, fraction = 2) @PositiveOrZero
    private BigDecimal costoActividad;

    private LocalDate fecPagoSenia;

    @Column(name = "imp_pagado", precision = 10, scale = 2)
    @Digits(integer = 8, fraction = 2) @PositiveOrZero
    private BigDecimal importePagado;

    @Column(name = "imp_a_pagar", precision = 10, scale = 2)
    @Digits(integer = 8, fraction = 2) @PositiveOrZero
    private BigDecimal importeAPagar;

    @Column(name = "sdo_pendiente", insertable = false, updatable = false)
    private BigDecimal saldoPendiente;

    private LocalDate fecConfReserva;
    private LocalTime horaConfReserva;

    private Boolean resCancelada = false;

    /* ——————————— Relaciones ——————————— */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_espacio", nullable = false)
    private Espacio espacio;
}

