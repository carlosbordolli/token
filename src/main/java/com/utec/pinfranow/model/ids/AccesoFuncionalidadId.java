package com.utec.pinfranow.model.ids;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccesoFuncionalidadId implements Serializable {
    private Integer idPerfil;
    private Integer idFuncionalidad;
}