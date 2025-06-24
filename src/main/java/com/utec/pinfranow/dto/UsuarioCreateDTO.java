package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;


@Data
@Schema(name = "Usuario Create DTO", description = "Creación de un usuario dentro del sistema")
public class UsuarioCreateDTO {

    @Schema(description = "Identificador interno del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private String nroDocumento;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Primer nombre del usuario", example = "Juan")
    private String priNombre;

    @Schema(description = "Segundo nombre del usuario", example = "Manuel")
    private String segNombre;


    @NotBlank(message = "El apellido no puede estar vacío")
    @Schema(description = "Primer apellido del usuario", example = "Perez")
    private String priApellido;

    @Schema(description = "Segundo apellido del usuario", example = "Gonzalez")
    private String segApellido;

    @Schema(description = "Tipo de documento del usuario", example = "CI, Pasaporte, DNI u otro")
    private String tipoDoc;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Schema(description = "Fecha de nacimiento del usuario", example = "31/12/2000")
    private LocalDate fecNacimiento;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El mail debe tener un formato válido. Ej. 'nombreapellido@dominio.com'.")
    @Schema(description = "Email del usuario", example = "nombreapellido@domain.com")
    @Email(message = "El mail debe ser una dirección de correo electrónico con formato correcto")
    private String email;

    @Schema(description = "Estado del usuario", example = "Activo")
    private String usuEstado;

    @Schema(description = "Calle donde reside el usuario", example = "Bulevar Artigas")
    private String calle;

    @Schema(description = "Numero de puerta donde reside del usuario", example = "1097")
    private String nroPuerta;

    @Schema(description = "Número de apartamento donde reside el usuario", example = "401")
    private String nroApartamento;

    @Schema(description = "Número de domicilio bis", example = "false")
    private Boolean bis;

    @Schema(description = "Código postal donde reside el usuario", example = "50000")
    private String codPostal;

    @Schema(description = "Id interno de la ciudad en la que reside el usuario", example = "2")
    private Integer idCiudad;

    @NotNull(message = "El perfil del usuario es obligatorio")
    @Schema(description = "Identificador interno del perfil del usuario", example = "3")
    private Integer idPerfil;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Schema(description = "Contraseña que ingresa el usuario")
    private String contrasena;
}

