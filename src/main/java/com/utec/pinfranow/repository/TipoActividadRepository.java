package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.TipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface TipoActividadRepository extends JpaRepository<TipoActividad, Integer> {
    boolean existsByNombre(String nombre);
}
