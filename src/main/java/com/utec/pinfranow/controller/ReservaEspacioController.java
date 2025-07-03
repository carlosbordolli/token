package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.ReservaEspacioCreateDTO;
import com.utec.pinfranow.dto.ReservaEspacioDTO;
import com.utec.pinfranow.service.ReservaEspacioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
    @RequestMapping("/api/reservas")
    @RequiredArgsConstructor
    @Tag(name = "Reserva de espacios", description = "Operaciones de reservas y cancelaciones de espacios para actividades")
    public class ReservaEspacioController {

        private final ReservaEspacioService reservaEspacioService;

    @Operation(summary = "Reservar un espacio", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Reserva realizada exitosamente")
    @PostMapping
    public ResponseEntity<ReservaEspacioDTO> reservar(@Valid @RequestBody ReservaEspacioCreateDTO dto) {
        ReservaEspacioDTO reserva = reservaEspacioService.create(dto);
        return ResponseEntity.status(201).body(reserva);
    }

    @Operation(summary = "Cancelar una reserva", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        boolean cancelada = reservaEspacioService.cancelarReserva(id);
        if (!cancelada) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reporte de reservas/cancelaciones por fecha", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/reporte/por-fecha")
    public ResponseEntity<List<ReservaEspacioDTO>> reportePorFecha(@RequestParam LocalDate desde, @RequestParam LocalDate hasta) {
        List<ReservaEspacioDTO> lista = reservaEspacioService.findByFecha(desde, hasta);
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Reporte de reservas/cancelaciones por espacio", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/reporte/por-espacio/{idEspacio}")
    public ResponseEntity<List<ReservaEspacioDTO>> reportePorEspacio(@PathVariable Integer idEspacio) {
        List<ReservaEspacioDTO> lista = reservaEspacioService.findByEspacio(idEspacio);
        return ResponseEntity.ok(lista);
    }
}
