package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

@Schema(name = "Perfil Create DTO", description = "Creación de un perfil dentro del sistema")
public class PerfilCreateDTO {

    @Schema(description = "Nombre del perfil", example = "Invitado")
    private String nomPerfil;

    @Schema(description = "Descripción del perfil", example = "Este perfil es para invitados")
    private String desPerfil;

    @Schema(description = "Estado del perfil", example = "ACTIVO")
    private String perEstado;
}

