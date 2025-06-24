package com.utec.pinfranow.model.ids;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data @NoArgsConstructor @AllArgsConstructor
public class InscripcionActividadId implements Serializable {
    private Integer usuarioId;
    private Integer actividadId;
}

