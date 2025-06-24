package com.utec.pinfranow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "aux_administrativos")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuxAdministrativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aux_administrativo")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    @NotNull
    private Usuario usuario;
}

