package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.ActividadDTO;
import com.utec.pinfranow.mapper.ActividadMapper;
import com.utec.pinfranow.model.*;
import com.utec.pinfranow.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
@Tag(name = "Actividades", description = "Operaciones relacionadas con actividades")
public class ActividadController {

    private final ActividadService actividadService;
    private final TipoActividadService tipoActividadService;
    private final AuxAdministrativoService auxAdministrativoService;
    private final EspacioService espacioService;

    private final ActividadMapper actividadMapper;

    @Operation(summary = "Listar todas las actividades", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Listado de actividades obtenido correctamente")
    @GetMapping
    public List<ActividadDTO> getAll() {
        return actividadService.findAll()
                .stream()
                .map(actividadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener una actividad por su ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actividad encontrada"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActividadDTO> getById(@PathVariable Integer id) {
        Optional<Actividad> actividadOpt = actividadService.findById(id);
        return actividadOpt
                .map(actividad -> ResponseEntity.ok(actividadMapper.toDto(actividad)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva actividad", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Actividad creada correctamente")
    @PostMapping
    public ResponseEntity<ActividadDTO> create(@Valid @RequestBody ActividadDTO dto) {

        TipoActividad tipo = tipoActividadService.findById(dto.getIdTipoActividad())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de actividad no encontrado"));

        AuxAdministrativo aux = auxAdministrativoService.findById(dto.getIdAuxAdministrativo())
                .orElseThrow(() -> new IllegalArgumentException("Auxiliar administrativo no encontrado"));

        Espacio espacio = espacioService.findById(dto.getIdEspacio())
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));

        Actividad actividad = ActividadMapper.toEntity(dto, tipo, aux, espacio);
        Actividad nueva = actividadService.save(actividad);

        return ResponseEntity.status(201).body(actividadMapper.toDto(nueva));
    }

    @Operation(summary = "Actualizar una actividad", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actividad actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ActividadDTO> update(@PathVariable Integer id, @Valid @RequestBody ActividadDTO dto) {
        if (!actividadService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        TipoActividad tipo = tipoActividadService.findById(dto.getIdTipoActividad())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de actividad no encontrado"));

        AuxAdministrativo aux = auxAdministrativoService.findById(dto.getIdAuxAdministrativo())
                .orElseThrow(() -> new IllegalArgumentException("Auxiliar administrativo no encontrado"));

        Espacio espacio = espacioService.findById(dto.getIdEspacio())
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));

        Actividad actividad = ActividadMapper.toEntity(dto, tipo, aux, espacio);
        actividad.setId(id); // Asegurarse que se actualiza el correcto
        Actividad actualizada = actividadService.save(actividad);

        return ResponseEntity.ok(actividadMapper.toDto(actualizada));
    }

    @Operation(summary = "Eliminar una actividad", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Actividad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!actividadService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        actividadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
