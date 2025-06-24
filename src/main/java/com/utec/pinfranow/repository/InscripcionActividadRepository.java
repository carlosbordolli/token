package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.InscripcionActividad;
import com.utec.pinfranow.model.ids.InscripcionActividadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionActividadRepository extends JpaRepository<InscripcionActividad, InscripcionActividadId> {
}