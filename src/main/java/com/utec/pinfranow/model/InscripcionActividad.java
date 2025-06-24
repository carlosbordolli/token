package com.utec.pinfranow.model;

import com.utec.pinfranow.model.ids.InscripcionActividadId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscripcion_actividades")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class InscripcionActividad {

    @EmbeddedId
    private InscripcionActividadId id;

    @ManyToOne(fetch = FetchType.LAZY)          // FK -> usuarios
    @MapsId("usuarioId")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)          // FK -> actividades
    @MapsId("actividadId")
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @CreationTimestamp
    @Column(name = "fec_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;

    @Column(name = "ins_cancelada")
    private Boolean cancelada = false;
}

