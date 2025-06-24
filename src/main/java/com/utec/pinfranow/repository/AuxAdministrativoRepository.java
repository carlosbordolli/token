package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.AuxAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Repository
public interface AuxAdministrativoRepository extends JpaRepository<AuxAdministrativo, Integer> {
    Optional<AuxAdministrativo> findByUsuarioIdUsuario(Integer idUsuario);
    boolean existsByUsuarioIdUsuario(Integer idUsuario);
}
