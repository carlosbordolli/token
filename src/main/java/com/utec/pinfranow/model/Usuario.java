package com.utec.pinfranow.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)// es un listener que detecta cada vez que hay un cambio y llena automaticamente los registros que tienen las anotacines de auditing
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nro_documento", length = 10, nullable = false, unique = true)
    private String nroDocumento;

    @Column(name = "pri_nombre", length = 50, nullable = false)
    private String priNombre;

    @Column(name = "seg_nombre", length = 50)
    private String segNombre;

    @Column(name = "pri_apellido", length = 50, nullable = false)
    private String priApellido;

    @Column(name = "seg_apellido", length = 50)
    private String segApellido;

    @Column(name = "tipo_doc", length = 10, nullable = false)
    private String tipoDoc;

    @Column(name = "fec_nacimiento", nullable = false)
    private LocalDate fecNacimiento;

    @Column(name = "mail", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "contrasena_hash", length = 255, nullable = false)
    private String contrasenaHash;

    @Column(name = "usu_estado", length = 12, nullable = false)
    private String usuEstado;

    @Column(name = "nro_socio", insertable = false, updatable = false)
    private Integer nroSocio;

    @Column(name = "calle", length = 100, nullable = false)
    private String calle;

    @Column(name = "nro_puerta", length = 10, nullable = false)
    private String nroPuerta;

    @Column(name = "nro_apartamento", length = 10)
    private String nroApartamento;

    @Column(name = "bis")
    private Boolean bis;

    @Column(name = "cod_postal", length = 10)
    private String codPostal;

    @ManyToOne
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @Column(name = "id_ciudad", nullable = false)
    private Integer idCiudad;

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