package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
@Builder // Permite la creación del DTO utilizando el patrón Builder

@Schema(name = "Usuario DTO", description = "Representa un usuario dentro del sistema")
public class UsuarioDTO {

    @Schema(description = "Identificador interno del usuario", example = "1")
    private Integer idUsuario;

    @Schema(description = "Numero de documento", example = "51671224")
    private String nroDocumento;

    @Schema(description = "Primer nombre del usuario", example = "Carlos")
    private String priNombre;

    @Schema(description = "Segundo nombre del usuario", example = "Leandro")
    private String segNombre;

    @Schema(description = "Primer apellido del usuario", example = "Melian")
    private String priApellido;

    @Schema(description = "Segundo apellido del usuario", example = "Lamas")
    private String segApellido;

    @Schema(description = "Tipo de documento del usuario", example = "CI")
    private String tipoDoc;

    @Schema(description = "Fecha de nacimiento del usuario", example = "31/12/2000")
    private LocalDate fecNacimiento;

    @Schema(description = "Email del usuario", example = "nombreapellido@domain.com")
    private String mail;

    @Schema(description = "Estado del usuario", example = "Activo")
    private String usuEstado;

    @Schema(description = "Número de socio del usuario", example = "12345")
    private Integer nroSocio;

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

    @Schema(description = "Identificador interno del perfil del usuario", example = "3")
    private Integer idPerfil;

    @NotNull(message = "Elija su Rol")
    @Schema(description = "Rol que elije el usuario")
    private Integer id_rol;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // para que Swagger no lo muestre ni lo permita modificar
    private String contrasenaHash;

    @Schema(description = "Lista de teléfonos del usuario")
    private List<TelefonoDTO> telefonos;

    @Schema(description = "Fecha de creación de el usuario", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha_creacion;

    @Schema(description = "Fecha de actualización de el usuario", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha_actualizacion;

    @Schema(description = "Usuario que realiza la creación", accessMode = Schema.AccessMode.READ_ONLY)
    private String usuario_creador;

    @Schema(description = "Usuario que realiza la actualización", accessMode = Schema.AccessMode.READ_ONLY)
    private String usuario_actualizador;

}