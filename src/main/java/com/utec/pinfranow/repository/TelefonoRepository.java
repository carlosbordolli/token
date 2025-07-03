package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.Telefono;
import com.utec.pinfranow.model.ids.TelefonoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, TelefonoId> {
    List<Telefono> findByIdUsuarioIdUsuario(Integer idUsuario);
    void deleteById(TelefonoId id);

    Optional<Telefono> findById(TelefonoId id);
}
