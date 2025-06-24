package com.utec.pinfranow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "espacios")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_espacio")
    private Integer id;

    @Column(name = "nom_espacio", length = 20, nullable = false, unique = true)
    @NotBlank @Size(max = 20)
    private String nombre;

    @NotNull @Positive
    private Integer capacidad;

    @Column(name = "precio_socio", precision = 10, scale = 2)
    @Digits(integer = 8, fraction = 2) @PositiveOrZero
    private BigDecimal precioSocio;

    @Column(name = "precio_no_socio", precision = 10, scale = 2)
    @Digits(integer = 8, fraction = 2) @PositiveOrZero
    private BigDecimal precioNoSocio;

    @Column(name = "vig_precio")
    private LocalDate vigenciaPrecios;

    @Column(name = "esp_observacion", length = 100)
    @Size(max = 100)
    private String observacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "esp_estado", length = 10, nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "espacio")
    private List<Actividad> actividades;

    @OneToMany(mappedBy = "espacio")
    private List<ReservaEspacio> reservas;
}

