package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Integer> {
    Optional<Espacio> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
