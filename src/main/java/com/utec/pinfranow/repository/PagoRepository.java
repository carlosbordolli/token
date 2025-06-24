package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByFechaCobroBetween(LocalDate desde, LocalDate hasta);

    List<Pago> findByActividadId(Integer idActividad);

    List<Pago> findByUsuarioIdUsuario(Integer idUsuario);

    @Query("SELECT p FROM Pago p WHERE p.usuario.idUsuario = :idUsuario AND p.fechaCobro BETWEEN :desde AND :hasta")
    List<Pago> findByUsuarioAndFecha(@Param("idUsuario") Integer idUsuario,
                                     @Param("desde") LocalDate desde,
                                     @Param("hasta") LocalDate hasta);
}
