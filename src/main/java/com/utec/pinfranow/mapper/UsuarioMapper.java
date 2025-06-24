package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.UsuarioCreateDTO;
import com.utec.pinfranow.dto.UsuarioDTO;
import com.utec.pinfranow.model.Perfil;
import com.utec.pinfranow.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDto(Usuario usuario) {
        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nroDocumento(usuario.getNroDocumento())
                .priNombre(usuario.getPriNombre())
                .segNombre(usuario.getSegNombre())
                .priApellido(usuario.getPriApellido())
                .segApellido(usuario.getSegApellido())
                .tipoDoc(usuario.getTipoDoc())
                .fecNacimiento(usuario.getFecNacimiento())
                .mail(usuario.getEmail())
                .usuEstado(usuario.getUsuEstado())
                .calle(usuario.getCalle())
                .nroPuerta(usuario.getNroPuerta())
                .nroApartamento(usuario.getNroApartamento())
                .bis(usuario.getBis())
                .codPostal(usuario.getCodPostal())
                .idCiudad(usuario.getIdCiudad())
                .idPerfil(usuario.getPerfil() != null ? usuario.getPerfil().getIdPerfil() : null)
                .build();
    }

    public Usuario toEntity(UsuarioCreateDTO dto) {
        return Usuario.builder()
                .nroDocumento(dto.getNroDocumento())
                .priNombre(dto.getPriNombre())
                .segNombre(dto.getSegNombre())
                .priApellido(dto.getPriApellido())
                .segApellido(dto.getSegApellido())
                .tipoDoc(dto.getTipoDoc())
                .fecNacimiento(dto.getFecNacimiento())
                .email(dto.getEmail())
                .usuEstado(dto.getUsuEstado())
                .calle(dto.getCalle())
                .nroPuerta(dto.getNroPuerta())
                .nroApartamento(dto.getNroApartamento())
                .bis(dto.getBis())
                .codPostal(dto.getCodPostal())
                .idCiudad(dto.getIdCiudad())
                .perfil(Perfil.builder().idPerfil(dto.getIdPerfil()).build())
                .build();
    }
}