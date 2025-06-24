package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
    boolean existsByNombre(String nombre);

    List<Actividad> findByEspacioIdAndFecha(Integer idEspacio, LocalDate fecha);

    // Buscar actividades que se sean en el mismo espacio y mismo día
    List<Actividad> findByEspacioIdAndFechaAndHora(Integer idEspacio, LocalDate fecha, LocalTime hora);
}
