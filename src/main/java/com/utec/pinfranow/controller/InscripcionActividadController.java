package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.FiltroReporteActividadDTO;
import com.utec.pinfranow.dto.FiltroReporteTipoActividadDTO;
import com.utec.pinfranow.dto.InscripcionActividadDTO;
import com.utec.pinfranow.mapper.InscripcionActividadMapper;
import com.utec.pinfranow.model.Actividad;
import com.utec.pinfranow.model.InscripcionActividad;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.model.ids.InscripcionActividadId;
import com.utec.pinfranow.service.ActividadService;
import com.utec.pinfranow.service.InscripcionActividadService;
import com.utec.pinfranow.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@Tag(name = "Inscripciones a Actividades", description = "Operaciones relacionadas con inscripciones")
public class InscripcionActividadController {

    private final InscripcionActividadService inscripcionActividadService;
    private final InscripcionActividadMapper inscripcionActividadMapper;
    private final UsuarioService usuarioService;
    private final ActividadService actividadService;
    @Operation(summary = "Listar todas las inscripciones", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public List<InscripcionActividadDTO> getAll() {
        return inscripcionActividadService.findAll()
                .stream()
                .map(inscripcionActividadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener una inscripción por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripción encontrada"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @GetMapping("/{idUsuario}/{idActividad}")
    public ResponseEntity<InscripcionActividadDTO> getById(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idActividad
    ) {
        InscripcionActividadId id = new InscripcionActividadId(idUsuario, idActividad);
        Optional<InscripcionActividad> opt = inscripcionActividadService.findById(id);
        return opt.map(inscripcion -> ResponseEntity.ok(inscripcionActividadMapper.toDto(inscripcion)))
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Registrar una inscripción", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Inscripción realizada correctamente")
    @PostMapping
    public ResponseEntity<InscripcionActividadDTO> create(@Valid @RequestBody InscripcionActividadDTO dto) {
        // Buscar Usuario por id
        Usuario usuario = usuarioService.findById(dto.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Buscar Actividad por id
        Actividad actividad = actividadService.findById(dto.getIdActividad())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));

        // Validar período de inscripción
        LocalDate hoy = LocalDate.now();
        if (actividad.getFecAperturaInscripcion() != null && actividad.getFecCierreInscripcion() != null) {
            if (hoy.isBefore(actividad.getFecAperturaInscripcion()) || hoy.isAfter(actividad.getFecCierreInscripcion())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El período de inscripción para esta actividad no está abierto actualmente.");
            }
        }

        // Mapear entidad con los 3 argumentos requeridos
        InscripcionActividad entity = inscripcionActividadMapper.toEntity(dto, usuario, actividad);

        // Guardar entidad
        InscripcionActividad nueva = inscripcionActividadService.save(entity);

        // Devolver DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionActividadMapper.toDto(nueva));
    }

    @Operation(summary = "Eliminar una inscripción", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inscripción eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @DeleteMapping("/{idUsuario}/{idActividad}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idActividad
    ) {
        InscripcionActividadId id = new InscripcionActividadId(idUsuario, idActividad);
        if (!inscripcionActividadService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        inscripcionActividadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancelar una inscripción", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Inscripción cancelada correctamente")
    @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    @PutMapping("/cancelar")
    public ResponseEntity<String> cancelarInscripcion(@RequestBody InscripcionActividadDTO dto) {
        InscripcionActividadId id = new InscripcionActividadId(dto.getIdUsuario(), dto.getIdActividad());
        boolean resultado = inscripcionActividadService.cancelarInscripcion(id);

        if (resultado) {
            return ResponseEntity.ok("Inscripción cancelada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la inscripción.");
        }
    }

    @Operation(
            summary = "Reporte de inscripciones/cancelaciones por actividades y rango de fechas",
            description = "Permite filtrar por fechas, tipo de movimiento (inscripción, cancelación o ambas) y actividades",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
    })
    @PostMapping("/reporte/actividades")
    public ResponseEntity<List<InscripcionActividadDTO>> obtenerReportePorActividades(
            @RequestBody FiltroReporteActividadDTO filtro) {

        List<InscripcionActividad> lista = inscripcionActividadService.obtenerReportePorActividades(filtro);

        List<InscripcionActividadDTO> resultado = lista.stream()
                .map(inscripcionActividadMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    @Operation(
            summary = "Reporte de inscripciones/cancelaciones por TIPO de actividad y fechas",
            description = "Permite filtrar por fechas, tipo de movimiento (inscripción, cancelación o ambas) y tipos de actividad",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @PostMapping("/reporte/tipo-actividad")
    public ResponseEntity<List<InscripcionActividadDTO>> obtenerReportePorTipoActividad(
            @RequestBody FiltroReporteTipoActividadDTO filtro) {

        List<InscripcionActividad> lista = inscripcionActividadService.obtenerReportePorTipoActividad(filtro);

        List<InscripcionActividadDTO> resultado = lista.stream()
                .map(inscripcionActividadMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

}
