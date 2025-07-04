package com.utec.pinfranow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "perfiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)// es un listener que detecta cada vez que hay un cambio y llena automaticamente los registros que tienen las anotacines de auditing
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Integer idPerfil;


    @NotBlank(message = "El nombre del perfil no puede estar vacío")
    @Column(name = "nom_perfil", length = 10, nullable = false)
    private String nomPerfil;

    @Column(name = "des_perfil", length = 50)
    private String desPerfil;

    @Column(name = "per_estado", length = 10, nullable = false)
    private String perEstado;

    //columnas para auditoría

    @CreatedDate // detecta cuando se crea un usuario
    @Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion; //se usa localDateTime porque nos deja registrar la hora

    @LastModifiedDate //detecta la ultima actualizaciónq ue tuvo un usuario
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fecha_actualizacion;

    @CreatedBy //por quien fue creado el usuario
    @Column(name = "usuario_creador")
    private String usuario_creador;

    @LastModifiedBy // quien modificó el usuario
    @Column(name = "usuario_actualizador")
    private String usuario_actualizador;
}
