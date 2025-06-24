package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.RegistroActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroActividadRepository extends JpaRepository<RegistroActividad, Integer> {

}
