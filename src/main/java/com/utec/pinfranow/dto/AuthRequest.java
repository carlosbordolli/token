package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Solicitud de autenticación", description = "Solicitud a la API para verificar la identidad de un usuario que intenta accedera los recursos")
public class AuthRequest {

    @Schema(description = "Nombre de usuario", example = "user")
    private String username;

    @Schema(description = "Contraseña", example = "*******")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

