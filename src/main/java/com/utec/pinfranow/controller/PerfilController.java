package com.utec.pinfranow.controller;
import com.utec.pinfranow.mapper.PerfilMapper;
import com.utec.pinfranow.dto.PerfilCreateDTO;
import com.utec.pinfranow.dto.PerfilDTO;
import com.utec.pinfranow.model.Perfil;
import com.utec.pinfranow.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/perfiles")
@RequiredArgsConstructor
@Tag(name = "Perfiles", description = "Operaciones relacionadas con perfiles de usuario")
@Validated
public class PerfilController {

    private final PerfilService perfilService;
    private final PerfilMapper perfilMapper;


    @Operation(summary = "Listar todos los perfiles", security = @SecurityRequirement(name = "bearerAuth"))

    @ApiResponse(responseCode = "200", description = "Listado de perfiles obtenido correctamente")
    @GetMapping
    public List<PerfilDTO> getAll() {
        return perfilService.findAll()
                .stream()
                .map(perfilMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un perfil por su ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getById(@PathVariable Integer id) {
        return perfilService.findById(id)
                .map(perfil -> ResponseEntity.ok(perfilMapper.toDto(perfil)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo perfil", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Perfil creado exitosamente")
    @PostMapping
    public ResponseEntity<PerfilDTO> create(@Valid @RequestBody PerfilCreateDTO dto) {
        Perfil perfil = perfilMapper.toEntity(dto);
        Perfil nuevo = perfilService.save(perfil);
        return ResponseEntity.status(201).body(perfilMapper.toDto(nuevo));
    }

    @Operation(summary = "Actualizar un perfil existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> update(@PathVariable Integer id, @Valid @RequestBody PerfilCreateDTO dto) {
        if (!perfilService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Perfil perfil = perfilMapper.toEntity(dto);
        perfil.setIdPerfil(id);
        Perfil actualizado = perfilService.save(perfil);
        return ResponseEntity.ok(perfilMapper.toDto(actualizado));
    }

    @Operation(summary = "Eliminar un perfil por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Perfil eliminado"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!perfilService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        perfilService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
