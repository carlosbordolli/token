package com.utec.pinfranow.model.ids;

import com.utec.pinfranow.model.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable @Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoId implements Serializable {
    @Column(name = "nro_telefono", length = 15, nullable = false)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
