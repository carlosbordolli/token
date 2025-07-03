package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.UsuarioSelfUpdateDTO;
import com.utec.pinfranow.mapper.UsuarioMapper;
import com.utec.pinfranow.dto.UsuarioCreateDTO;
import com.utec.pinfranow.dto.UsuarioDTO;
import com.utec.pinfranow.model.Perfil;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.service.EmailService;


import com.utec.pinfranow.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final EmailService emailService;

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
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO dto, Authentication auth) {
        // Obtener el rol de quien hace la petición
        String rolActual = auth.getAuthorities().stream()
                .map(granted -> granted.getAuthority())
                .findFirst()
                .orElse(null);

        // Si no es ADMIN ni AUXADMIN, fuerza el rol SIN_VERIFICAR
        if (!"ROLE_ADMIN".equals(rolActual) && !"ROLE_AUXADMIN".equals(rolActual)) {
            dto.setId_rol(5); // 5 = SIN_VERIFICAR
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setContrasenaHash(passwordEncoder.encode(dto.getContrasena()));
        Usuario nuevo = usuarioService.save(usuario, dto.getTelefonos());
        emailService.enviarBienvenida(nuevo.getEmail(), nuevo.getPriNombre());

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
        Usuario actualizado = usuarioService.save(usuario,dto.getTelefonos());

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

    @Operation(summary = "Listar usuarios sin verificar (idRol = 5)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Listado de usuarios sin verificar obtenido correctamente")
    @GetMapping("/sin-verificar")
    public List<UsuarioDTO> getSinVerificar() {
        return usuarioService.findUsuariosSinVerificar()
                .stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @PutMapping("/me")
    @Operation(summary = "Actualizar mis datos personales", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente")
    public ResponseEntity<UsuarioDTO> updateMyData(@RequestBody UsuarioSelfUpdateDTO dto, Authentication auth) {
        String email = auth.getName(); // extraído del JWT

        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setPriNombre(dto.getPriNombre());
        usuario.setSegNombre(dto.getSegNombre());
        usuario.setPriApellido(dto.getPriApellido());
        usuario.setSegApellido(dto.getSegApellido());
        usuario.setFecNacimiento(dto.getFecNacimiento());
        usuario.setCalle(dto.getCalle());
        usuario.setNroPuerta(dto.getNroPuerta());
        usuario.setNroApartamento(dto.getNroApartamento());
        usuario.setBis(dto.getBis());
        usuario.setCodPostal(dto.getCodPostal());
        usuario.setIdCiudad(dto.getIdCiudad());
        usuario.setPerfil(Perfil.builder().idPerfil(dto.getIdPerfil()).build());

        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            usuario.setContrasenaHash(passwordEncoder.encode(dto.getContrasena()));
        }
        Usuario actualizado = usuarioService.save(usuario,dto.getTelefonos());

        return ResponseEntity.ok(usuarioMapper.toDto(actualizado));
    }
}
