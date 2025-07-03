package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.TipoActividadCreateDTO;
import com.utec.pinfranow.dto.TipoActividadDTO;
import com.utec.pinfranow.mapper.ActividadMapper;
import com.utec.pinfranow.mapper.TipoActividadMapper;
import com.utec.pinfranow.model.Actividad;
import com.utec.pinfranow.model.TipoActividad;
import com.utec.pinfranow.service.TipoActividadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipo-actividad")
@RequiredArgsConstructor
@Tag(name = "Tipo de Actividades", description = "Operaciones para gestionar los tipos de actividades")
@Validated
public class TipoActividadController {

    private final TipoActividadService tipoActividadService;
    private final TipoActividadMapper tipoActividadMapper;
    @Operation(summary = "Listar todos los tipos de activdades", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Listado de tipo de actividades obtenido correctamente")
    @GetMapping
    public List<TipoActividadDTO> getAll() {
        return tipoActividadService.findAll().stream()
                .map(TipoActividadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un tipo de actividad por su ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de ctividad encontrada"),
            @ApiResponse(responseCode = "404", description = "Tipo de actividad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoActividadDTO> getById(@PathVariable Integer id) {
        Optional<TipoActividad> tipoActividadOpt = tipoActividadService.findById(id);
        return tipoActividadOpt
                .map(actividad -> ResponseEntity.ok(TipoActividadMapper.toDto(actividad)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de actividad", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Tipo de actividad creado exitosamente")
    public ResponseEntity<TipoActividadDTO> create(@Valid @RequestBody TipoActividadCreateDTO dto) {

        TipoActividad tipoActividad = TipoActividadMapper.toEntity(dto);
        TipoActividad nuevo = tipoActividadService.save(tipoActividad);

        return ResponseEntity.status(201).body(tipoActividadMapper.toDto(nuevo));
    }

    @Operation(summary = "Modificar un tipo de actividad", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de actividad actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de actividad no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TipoActividadDTO> update(@PathVariable Integer id, @Valid @RequestBody TipoActividadDTO dto) {
        Optional<TipoActividad> existente = tipoActividadService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TipoActividad tipoActividad = TipoActividadMapper.toEntity(dto);
        tipoActividad.setId(id); // aseguramos que el ID venga del path

        TipoActividad actualizado = tipoActividadService.save(tipoActividad);
        return ResponseEntity.ok(TipoActividadMapper.toDto(actualizado));
    }

    @Operation(summary = "Eliminar un tipo de actividad  por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de actividad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de actividad no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (tipoActividadService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        tipoActividadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
