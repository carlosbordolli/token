package com.utec.pinfranow.model;
import com.utec.pinfranow.model.ids.AccesoFuncionalidadId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "acceso_funcionalidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccesoFuncionalidad {

    @EmbeddedId
    private AccesoFuncionalidadId id;

    @ManyToOne
    @MapsId("idPerfil")
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @ManyToOne
    @MapsId("idFuncionalidad")
    @JoinColumn(name = "id_funcionalidad")
    private Funcionalidad funcionalidad;
}