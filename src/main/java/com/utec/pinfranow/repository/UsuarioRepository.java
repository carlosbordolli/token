package com.utec.pinfranow.repository;

import com.utec.pinfranow.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
        boolean existsByEmail(String Email);
        Optional<Usuario> findByEmail(String email);

        List<Usuario> findAllByIdRol(Integer idRol);
}