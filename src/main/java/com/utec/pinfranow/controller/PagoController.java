package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.PagoDTO;
import com.utec.pinfranow.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Operaciones relacionadas con los pagos realizados por usuarios")
@Validated
public class PagoController {

    private final PagoService pagoService;

    @Operation(summary = "Listar todos los pagos", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Listado de pagos obtenido correctamente")
    @GetMapping
    public List<PagoDTO> getAll() {
        return pagoService.findAll();
    }

    @Operation(summary = "Obtener un pago por su ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> getById(@PathVariable Integer id) {
        PagoDTO pago = pagoService.findById(id);
        if (pago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pago);
    }

    @Operation(summary = "Crear un nuevo pago", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Pago creado exitosamente")
    @PostMapping
    public ResponseEntity<PagoDTO> create(@Valid @RequestBody PagoDTO dto) {
        PagoDTO nuevo = pagoService.save(dto);
        return ResponseEntity.status(201).body(nuevo);
    }

    @Operation(summary = "Actualizar un pago existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> update(@PathVariable Integer id, @Valid @RequestBody PagoDTO dto) {
        if (pagoService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        dto.setIdPago(id);
        PagoDTO actualizado = pagoService.save(dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar un pago por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (pagoService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        pagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar pagos entre fechas", description = "Devuelve los pagos realizados en un rango de fechas",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/fecha")
    public List<PagoDTO> pagosPorFecha(@RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                       @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return pagoService.findByFecha(desde, hasta);
    }

    @Operation(summary = "Listar pagos por actividad", description = "Devuelve los pagos relacionados a una actividad",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/actividad/{idActividad}")
    public List<PagoDTO> pagosPorActividad(@PathVariable Integer idActividad) {
        return pagoService.findByActividad(idActividad);
    }

    @Operation(summary = "Listar pagos de un usuario en un rango de fechas",
            description = "Devuelve los pagos realizados por un usuario en un rango de fechas",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/usuario/{idUsuario}/fecha")
    public List<PagoDTO> pagosDeUsuarioPorFecha(@PathVariable Integer idUsuario,
                                                @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                                @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return pagoService.findByUsuarioAndFecha(idUsuario, desde, hasta);
    }


}
