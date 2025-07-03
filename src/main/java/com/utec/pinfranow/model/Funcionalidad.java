package com.utec.pinfranow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "funcionalidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionalidad")
    private Integer id;

    @Column(name = "nom_funcionalidad", length = 10, nullable = false, unique = true)
    private String nombre;

    @Column(name = "des_funcionalidad", length = 50)
    private String descripcion;

    @Column(name = "fun_estado", nullable = false)
    private String estado;
}
