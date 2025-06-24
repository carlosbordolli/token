package com.utec.pinfranow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "actividades")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Integer id;

    @Column(name = "actividad_nom", length = 100, nullable = false, unique = true)
    @NotBlank @Size(max = 100)
    private String nombre;

    @Column(name = "acrividad_descripcion", length = 255)
    @Size(max = 255)
    private String descripcion;

    @Size(max = 255)
    private String objetivo;

    @Column(name = "fec_actividad", nullable = false)
    @NotNull
    private LocalDate fecha;

    @Column(name = "hora_activdad", nullable = false)
    @NotNull
    private LocalTime hora;

    private LocalTime duracion;

    private Boolean inscripcion;

    @PositiveOrZero
    private Integer costo;

    private LocalDate fecInscripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", length = 10, nullable = false)
    private TipoPago tipoPago;

    @Column(name = "act_observaciones", length = 100)
    @Size(max = 100)
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "act_estado", length = 10, nullable = false)
    private Estado estado;

    /* ——————————— Relaciones ——————————— */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_actividad")
    private TipoActividad tipoActividad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aux_administrativo")
    private AuxAdministrativo auxAdministrativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_espacio")
    private Espacio espacio;

    @OneToMany(mappedBy = "actividad")
    private List<InscripcionActividad> inscripciones;
}
