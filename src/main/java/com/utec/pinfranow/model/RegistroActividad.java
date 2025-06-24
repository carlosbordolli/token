package com.utec.pinfranow.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_actividad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Schema(name = "Registro Actividad", description = "Representa el registro de una actividad dentro del sistema")
public class RegistroActividad {

    @Schema(description = "Identificador interno del registro", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Integer id;

    @Schema(description = "Nombre del usuario", example = "Juan Perez", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "usuario", length = 100)
    private String usuario;

    @Schema(description = "Método del registro", example = "Telefónico", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "metodo", length = 10)
    private String metodo;

    @Column(name = "endpoint", length = 255)
    private String endpoint;

    @Schema(description = "Fecha y hora del registro", example = "31/12/2024", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;
}
