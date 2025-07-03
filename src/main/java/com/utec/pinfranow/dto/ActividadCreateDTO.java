package com.utec.pinfranow.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActividadCreateDTO {
    private String nombre;
    private String descripcion;
    private String objetivo;
    private LocalDate fecha;
    private LocalTime hora;
    private LocalTime duracion;
    private Boolean inscripcion;
    private Integer costo;
    private LocalDate fecInscripcion;
    private LocalDate fecAperturaInscripcion;
    private LocalDate fecCierreInscripcion;
    private String tipoPago;
    private String observaciones;
    private String estado;

    private Integer idTipoActividad;
    private Integer idAuxAdministrativo;
    private Integer idEspacio;
}