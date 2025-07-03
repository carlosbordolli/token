package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.FuncionalidadCreateDTO;
import com.utec.pinfranow.dto.FuncionalidadDTO;
import com.utec.pinfranow.service.FuncionalidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionalidades")
@RequiredArgsConstructor
@Tag(name = "Funcionalidad", description = "Operaciones relacionadas con las funcionalidades")
public class FuncionalidadController {

    private final FuncionalidadService funcionalidadService;

    @Operation(summary = "Listar todas las funcionalidades")
    @ApiResponse(responseCode = "200", description = "Listado completo de funcionalidades")
    @GetMapping
    public ResponseEntity<List<FuncionalidadDTO>> listar() {
        return ResponseEntity.ok(funcionalidadService.findAll());
    }

    @Operation(summary = "Obtener una funcionalidad por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionalidad encontrada"),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FuncionalidadDTO> obtenerPorId(@PathVariable Integer id) {
        FuncionalidadDTO dto = funcionalidadService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear una nueva funcionalidad")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionalidad creada exitosamente",
                    content = @Content(schema = @Schema(implementation = FuncionalidadDTO.class)))
    })
    @PostMapping
    public ResponseEntity<FuncionalidadDTO> crear(@RequestBody FuncionalidadCreateDTO dto) {
        return ResponseEntity.status(201).body(funcionalidadService.save(dto));
    }

    @Operation(summary = "Actualizar una funcionalidad existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionalidad actualizada"),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FuncionalidadDTO> actualizar(@PathVariable Integer id,
                                                       @RequestBody FuncionalidadCreateDTO dto) {
        FuncionalidadDTO updated = funcionalidadService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar una funcionalidad por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Funcionalidad eliminada"),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        boolean deleted = funcionalidadService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}