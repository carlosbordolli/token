package com.utec.pinfranow.model;

import com.utec.pinfranow.model.ids.TelefonoId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telefonos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Telefono {
    @EmbeddedId
    private TelefonoId id;
}
