package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.TelefonoDTO;
import com.utec.pinfranow.mapper.UsuarioMapper;
import com.utec.pinfranow.model.Telefono;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.model.ids.TelefonoId;
import com.utec.pinfranow.repository.TelefonoRepository;
import com.utec.pinfranow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fabdelgado.ciuy.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TelefonoRepository telefonoRepository;
    private final UsuarioMapper usuarioMapper;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    public Usuario save(Usuario usuario, List<TelefonoDTO> telefonoDTOs) {
        CustomValidator validador = new CustomValidator();


        if (usuario.getFecNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura.");
        }

        // ACTUALIZACIÓN DE USUARIO EXISTENTE
        if (usuario.getIdUsuario() != null && usuarioRepository.existsById(usuario.getIdUsuario())) {
            Usuario existente = usuarioRepository.findById(usuario.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // ✅ Actualizar campos editables
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

            // ✅ Teléfonos: eliminar los anteriores
            telefonoRepository.deleteAllById(
                    existente.getTelefonos().stream()
                            .map(Telefono::getId)
                            .toList()
            );
            existente.getTelefonos().clear();

            // ✅ Agregar los nuevos teléfonos
            if (telefonoDTOs != null && !telefonoDTOs.isEmpty()) {
                List<Telefono> nuevosTelefonos = usuarioMapper.mapTelefonos(telefonoDTOs, existente);
                telefonoRepository.saveAll(nuevosTelefonos);
                existente.setTelefonos(nuevosTelefonos);
            }

            return usuarioRepository.save(existente);
        }

        // NUEVO USUARIO
        else {
            usuario.setTelefonos(null); // evitamos que JPA intente persistir antes de tiempo

            Usuario nuevoUsuario = usuarioRepository.save(usuario); // se guarda con ID

            // ✅ Asignar y guardar teléfonos si vienen en el DTO
            if (telefonoDTOs != null && !telefonoDTOs.isEmpty()) {
                List<Telefono> telefonos = usuarioMapper.mapTelefonos(telefonoDTOs, nuevoUsuario);
                telefonoRepository.saveAll(telefonos);
                nuevoUsuario.setTelefonos(telefonos); // opcional para devolver completo
            }

            return nuevoUsuario;
        }
    }


    public boolean deleteById(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Usuario> findUsuariosSinVerificar() {
        return usuarioRepository.findAllByIdRol(5);
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    public boolean existsById(Integer id) {
        return usuarioRepository.existsById(id);
    }
}
