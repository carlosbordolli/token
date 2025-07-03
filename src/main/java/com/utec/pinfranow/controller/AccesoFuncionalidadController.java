package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.AccesoFuncionalidadCreateDTO;
import com.utec.pinfranow.dto.AccesoFuncionalidadDTO;
import com.utec.pinfranow.model.ids.AccesoFuncionalidadId;
import com.utec.pinfranow.service.AccesoFuncionalidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accesos-funcionalidades")
@RequiredArgsConstructor
@Tag(name = "Acceso a Funcionalidades", description = "Accesos de perfiles a funcionalidades específicas")
public class AccesoFuncionalidadController {

    private final AccesoFuncionalidadService service;

    @GetMapping
    @Operation(summary = "Listar todos los accesos")
    public ResponseEntity<List<AccesoFuncionalidadDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/perfil/{idPerfil}")
    @Operation(summary = "Listar accesos por perfil")
    public ResponseEntity<List<AccesoFuncionalidadDTO>> getByPerfil(@PathVariable Integer idPerfil) {
        return ResponseEntity.ok(service.findByPerfil(idPerfil));
    }

    @PostMapping
    @Operation(summary = "Crear acceso de un perfil a una funcionalidad")
    public ResponseEntity<AccesoFuncionalidadDTO> create(@RequestBody AccesoFuncionalidadCreateDTO dto) {
        AccesoFuncionalidadDTO creado = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{idPerfil}/{idFuncionalidad}")
    @Operation(summary = "Eliminar acceso a una funcionalidad")
    public ResponseEntity<Void> delete(@PathVariable Integer idPerfil,
                                       @PathVariable Integer idFuncionalidad) {
        boolean eliminado = service.deleteById(new AccesoFuncionalidadId(idPerfil, idFuncionalidad));
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
