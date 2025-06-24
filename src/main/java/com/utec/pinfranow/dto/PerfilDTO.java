package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "PerfilDTO", description = "Representa un perfil dentro del sistema")
public class PerfilDTO {

    @Schema(description = "Identificador interno del perfil", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idPerfil;

    @Schema(description = "Nombre del perfil", example = "ADMIN")
    private String nomPerfil;

    @Schema(description = "Descripción del perfil", example = "Perfil administrativo con permisos completos")
    private String desPerfil;

    @Schema(description = "Estado del perfil ej, ACTIVO, INACTIVO", example = "ACTIVO")
    private String perEstado;

    @CreatedDate
    @Schema(description = "Fecha de creación del perfil", example = "2024-06-01T10:00:00")
    private LocalDateTime fecha_creacion;

    @LastModifiedDate
    @Schema(description = "Fecha de última actualización del perfil", example = "2024-06-10T15:30:00")
    private LocalDateTime fecha_actualizacion;

    @CreatedBy
    @Schema(description = "Usuario que creó el perfil", example = "admin")
    private String usuario_creador;

    @LastModifiedBy
    @Schema(description = "Usuario que actualizó el perfil por última vez", example = "user")
    private String usuario_actualizador;
}
