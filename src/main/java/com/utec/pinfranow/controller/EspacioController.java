package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.EspacioDTO;
import com.utec.pinfranow.mapper.EspacioMapper;
import com.utec.pinfranow.model.Espacio;
import com.utec.pinfranow.service.EspacioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
@Tag(name = "Espacios", description = "Operaciones relacionadas con los espacios")
public class EspacioController {

    private final EspacioService espacioService;
    private final EspacioMapper espacioMapper;

    @Operation(summary = "Listar todos los espacios", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public List<EspacioDTO> getAll() {
        return espacioService.findAll()
                .stream()
                .map(espacioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un espacio por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Espacio encontrado"),
            @ApiResponse(responseCode = "404", description = "Espacio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EspacioDTO> getById(@PathVariable Integer id) {
        Optional<Espacio> espacioOpt = espacioService.findById(id);
        return espacioOpt
                .map(espacio -> ResponseEntity.ok(espacioMapper.toDto(espacio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo espacio", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Espacio creado exitosamente")
    @PostMapping
    public ResponseEntity<EspacioDTO> create(@Valid @RequestBody EspacioDTO dto) {
        Espacio espacio = espacioMapper.toEntity(dto);
        Espacio nuevo = espacioService.save(espacio);
        return ResponseEntity.status(201).body(espacioMapper.toDto(nuevo));
    }

    @Operation(summary = "Actualizar un espacio existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Espacio actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Espacio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EspacioDTO> update(@PathVariable Integer id, @Valid @RequestBody EspacioDTO dto) {
        if (!espacioService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Espacio espacio = espacioMapper.toEntity(dto);
        espacio.setId(id);
        Espacio actualizado = espacioService.save(espacio);

        return ResponseEntity.ok(espacioMapper.toDto(actualizado));
    }

    @Operation(summary = "Eliminar un espacio por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Espacio eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Espacio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!espacioService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
