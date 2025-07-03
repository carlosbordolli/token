package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class FiltroReporteTipoActividadDTO {

    private LocalDate fechaDesde;

    private LocalDate fechaHasta;

    private List<Integer> idTiposActividad;

    @Schema(
            example = "INSCRIPCION/CANCELACION/AMBAS",
            description = "Tipo de movimiento a filtrar",
            accessMode = Schema.AccessMode.READ_ONLY
    )

    private String tipoMovimiento;
}