package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.AuxAdministrativoDTO;
import com.utec.pinfranow.mapper.AuxAdministrativoMapper;
import com.utec.pinfranow.model.AuxAdministrativo;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.service.AuxAdministrativoService;
import com.utec.pinfranow.service.UsuarioService;
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
@RequestMapping("/api/auxiliares")
@RequiredArgsConstructor
@Tag(name = "Auxiliares Administrativos", description = "Operaciones relacionadas con auxiliares administrativos")
public class AuxAdministrativoController {

    private final AuxAdministrativoService auxService;
    private final UsuarioService usuarioService;
    private final AuxAdministrativoMapper auxMapper;

    @Operation(summary = "Listar todos los auxiliares", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public List<AuxAdministrativoDTO> getAll() {
        return auxService.findAll()
                .stream()
                .map(auxMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener auxiliar por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auxiliar encontrado"),
            @ApiResponse(responseCode = "404", description = "Auxiliar no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuxAdministrativoDTO> getById(@PathVariable Integer id) {
        return auxService.findById(id)
                .map(aux -> ResponseEntity.ok(auxMapper.toDto(aux)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar auxiliar por ID de usuario", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auxiliar encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró auxiliar con ese usuario")
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<AuxAdministrativoDTO> getByUsuarioId(@PathVariable Integer idUsuario) {
        return auxService.findByUsuarioId(idUsuario)
                .map(aux -> ResponseEntity.ok(auxMapper.toDto(aux)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo auxiliar administrativo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Auxiliar creado correctamente")
    @PostMapping
    public ResponseEntity<AuxAdministrativoDTO> create(@Valid @RequestBody AuxAdministrativoDTO dto) {
        if (auxService.existsByUsuarioId(Math.toIntExact(dto.getIdUsuario()))) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> usuarioOpt = usuarioService.findById(Math.toIntExact(dto.getIdUsuario()));
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AuxAdministrativo nuevo = AuxAdministrativo.builder()
                .usuario(usuarioOpt.get())
                .build();

        AuxAdministrativo creado = auxService.save(nuevo);
        return ResponseEntity.status(201).body(auxMapper.toDto(creado));
    }

    @Operation(summary = "Eliminar auxiliar por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Auxiliar eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Auxiliar no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!auxService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
