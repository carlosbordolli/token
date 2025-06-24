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

    @Schema(description = "Nombre de la actividad", example = "Fisioterapia")
    private String nombre;

    @Schema(description = "Decripción de la actividad", example = "Disciplina de la salud que se enfoca en el desarrollo, conservación y restauración del bienestar físico de las personas a través de tratamientos terapéuticos")
    private String descripcion;

    @Schema(description = "Objetivo de la actividad", example = "Prevenir, tratar y rehabilitar diversas patologías y lesiones")
    private String objetivo;

    @Schema(description = "Fecha de la actividad", example = "31/12/2024")
    private LocalDate fecha;

    @Schema(description = "Hora de la actividad", example = "19:00")
    private LocalTime hora;

    @Schema(description = "Duración de la actividad", example = "1:00")
    private LocalTime duracion;

    @Schema(description = "Incripción a la actividad", example = "true")
    private Boolean inscripcion;

    @Schema(description = "Costo de la actividad", example = "500")
    private Integer costo;

    @Schema(description = "Fecha de incripción a la actividad", example = "31/12/2024")
    private LocalDate fecInscripcion;

    @Schema(description = "Tipo de pago de la actividad", example = "EFECTIVO")
    private String tipoPago;

    @Schema(description = "Observaciones de la actividad", example = "No recomendado para pacientes que presenten ciertas condiciones agudas o graves que requieran atención médica inmediata")
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

