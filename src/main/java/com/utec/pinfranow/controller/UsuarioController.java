package com.utec.pinfranow.controller;

import com.utec.pinfranow.mapper.UsuarioMapper;
import com.utec.pinfranow.dto.UsuarioCreateDTO;
import com.utec.pinfranow.dto.UsuarioDTO;
import com.utec.pinfranow.model.Usuario;

import com.utec.pinfranow.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    @Operation(summary = "Listar todos los usuarios", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Listado de usuarios obtenido correctamente")
    @GetMapping
    public List<UsuarioDTO> getAll() {
        return usuarioService.findAll()
                .stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un usuario por su ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Integer id) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);
        return usuarioOpt
                .map(usuario -> ResponseEntity.ok(usuarioMapper.toDto(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario con contraseña hasheada", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setContrasenaHash(passwordEncoder.encode(dto.getContrasena()));
        Usuario nuevo = usuarioService.save(usuario);
        return ResponseEntity.status(201).body(usuarioMapper.toDto(nuevo));
    }

    @Operation(summary = "Actualizar un usuario existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @Valid @RequestBody UsuarioCreateDTO dto) {
        if (!usuarioService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setIdUsuario(id);
        usuario.setContrasenaHash(passwordEncoder.encode(dto.getContrasena()));
        Usuario actualizado = usuarioService.save(usuario);

        return ResponseEntity.ok(usuarioMapper.toDto(actualizado));
    }

    @Operation(summary = "Eliminar un usuario por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!usuarioService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
