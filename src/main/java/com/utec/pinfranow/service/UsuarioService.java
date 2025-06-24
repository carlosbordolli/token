package com.utec.pinfranow.service;

import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fabdelgado.ciuy.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        CustomValidator validador = new CustomValidator();

         if(validador.validateCi(usuario.getNroDocumento())) {
             throw new IllegalArgumentException("Cédula inválida");
         }

        if (usuario.getIdUsuario() != null && usuarioRepository.existsById(usuario.getIdUsuario())) {
            Usuario existente = usuarioRepository.findById(usuario.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // ✅ Solo actualizamos campos editables
            existente.setNroDocumento(usuario.getNroDocumento());
            existente.setPriNombre(usuario.getPriNombre());
            existente.setSegNombre(usuario.getSegNombre());
            existente.setPriApellido(usuario.getPriApellido());
            existente.setSegApellido(usuario.getSegApellido());
            existente.setTipoDoc(usuario.getTipoDoc());
            existente.setFecNacimiento(usuario.getFecNacimiento());
            existente.setEmail(usuario.getEmail());
            existente.setContrasenaHash(usuario.getContrasenaHash());
            existente.setUsuEstado(usuario.getUsuEstado());
            existente.setCalle(usuario.getCalle());
            existente.setNroPuerta(usuario.getNroPuerta());
            existente.setNroApartamento(usuario.getNroApartamento());
            existente.setBis(usuario.getBis());
            existente.setCodPostal(usuario.getCodPostal());
            existente.setPerfil(usuario.getPerfil());
            existente.setIdCiudad(usuario.getIdCiudad());

            return usuarioRepository.save(existente);
        } else {
            // ✅ Alta nueva: se permite insertar campos de auditoría automáticamente
            return usuarioRepository.save(usuario);
        }
    }

    public boolean deleteById(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    public boolean existsById(Integer id) {
        return usuarioRepository.existsById(id);
    }
}
