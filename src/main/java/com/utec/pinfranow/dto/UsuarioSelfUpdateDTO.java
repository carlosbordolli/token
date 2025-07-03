package com.utec.pinfranow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

// UsuarioSelfUpdateDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSelfUpdateDTO {

    private String priNombre;
    private String segNombre;
    private String priApellido;
    private String segApellido;
    private LocalDate fecNacimiento;

    private String contrasena; // la nueva contraseña en texto plano

    private String calle;
    private String nroPuerta;
    private String nroApartamento;
    private Boolean bis;
    private String codPostal;

    private Integer idCiudad;
    private Integer idPerfil;

    @Schema(description = "Lista de teléfonos del usuario")
    private List<TelefonoDTO> telefonos;
}