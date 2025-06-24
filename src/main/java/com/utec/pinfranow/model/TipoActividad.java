package com.utec.pinfranow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tipo_actividades")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TipoActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_actividad")
    private Integer id;

    @Column(name = "tipo_act_nombre", length = 20, nullable = false, unique = true)
    @NotBlank @Size(max = 20)
    private String nombre;

    @Column(name = "tipo_act_descripcion", length = 100)
    @Size(max = 100)
    private String descripcion;

    private LocalDate fecBaja;

    @Column(name = "raz_baja", length = 100)
    @Size(max = 100)
    private String razonBaja;

    @Size(max = 200)
    private String comentario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_act_estado", length = 10, nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "tipoActividad")
    private List<Actividad> actividades;
}




