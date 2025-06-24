package com.utec.pinfranow.controller;

import com.utec.pinfranow.model.RegistroActividad;
import com.utec.pinfranow.repository.RegistroActividadRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
@Tag(name = "Auditoría", description = "Operaciones para consultar registros de actividad")
public class RegistroActividadController {

    private final RegistroActividadRepository registroActividadRepository;

    @GetMapping
    @Operation(summary = "Listar todos los registros de actividad")
    public List<RegistroActividad> listar() {
        return registroActividadRepository.findAll();
    }
}
