package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Actividad DTO", description = "Representa una actividad dentro del sistema")
public class ActividadDTO {

    @Schema(description = "Identificador interno de la actividad", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nombre de la actividad", example = "Clase de baile")
    private String nombre;

    @Schema(description = "Decripción de la actividad", example = "Se realizan bailes recreacionales")
    private String descripcion;

    @Schema(description = "Objetivo de la actividad", example = "Actividad social para disfrutar")
    private String objetivo;

    @Schema(description = "Fecha de la actividad", example = "2025-07-29")
    private LocalDate fecha;

    @Schema(description = "Hora de la actividad", example = "19:00:00")
    private LocalTime hora;

    @Schema(description = "Duración de la actividad", example = "01:00:00")
    private LocalTime duracion;

    @Schema(description = "Incripción a la actividad", example = "true")
    private Boolean inscripcion;

    @Schema(description = "Costo de la actividad", example = "500")
    private Integer costo;

    @Schema(description = "Fecha de inscripción a la actividad", example = "2025-06-29")
    private LocalDate fecInscripcion;

    @Schema(description = "Fecha de apertura de inscripciones", example = "2025-06-29")
    private LocalDate fecAperturaInscripcion;

    @Schema(description = "Fecha de cierre de inscripciones", example = "2025-07-28")
    private LocalDate fecCierreInscripcion;

    @Schema(description = "Tipo de pago de la actividad", example = "EFECTIVO")
    private String tipoPago;

    @Schema(description = "Observaciones de la actividad", example = "No recomendado para pacientes autistas")
    private String observaciones;

    @Schema(description = "Estado de la actividad", example = "ACTIVO")
    private String estado;

    @Schema(description = "Identificador interno del tipo de actividad", example = "1")
    private Integer idTipoActividad;

    @Schema(description = "Identificador interno del auxiliar administrativo que la registra", example = "1")
    private Integer idAuxAdministrativo;

    @Schema(description = "Identificador interno del espacio donde se realiza", example = "1")
    private Integer idEspacio;
}