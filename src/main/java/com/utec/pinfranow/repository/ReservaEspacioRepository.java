package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.ReservaEspacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaEspacioRepository extends JpaRepository<ReservaEspacio, Integer> {

    List<ReservaEspacio> findByEspacioIdAndFecReservaActividad(Integer idEspacio, LocalDate fecha);
    List<ReservaEspacio> findByFecReservaActividadBetween(LocalDate desde, LocalDate hasta);
    List<ReservaEspacio> findByEspacioId(Integer idEspacio);

}
