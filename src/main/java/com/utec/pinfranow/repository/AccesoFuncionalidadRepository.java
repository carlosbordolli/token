package com.utec.pinfranow.repository;
import com.utec.pinfranow.model.AccesoFuncionalidad;
import com.utec.pinfranow.model.ids.AccesoFuncionalidadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccesoFuncionalidadRepository extends JpaRepository<AccesoFuncionalidad, AccesoFuncionalidadId> {

    List<AccesoFuncionalidad> findByPerfilIdPerfil(Integer idPerfil);

    boolean existsById(AccesoFuncionalidadId id);

}
