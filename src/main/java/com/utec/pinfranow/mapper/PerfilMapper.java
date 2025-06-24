package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.PerfilCreateDTO;
import com.utec.pinfranow.dto.PerfilDTO;
import com.utec.pinfranow.model.Perfil;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {

    public PerfilDTO toDto(Perfil perfil) {
        return PerfilDTO.builder()
                .idPerfil(perfil.getIdPerfil())
                .nomPerfil(perfil.getNomPerfil())
                .desPerfil(perfil.getDesPerfil())
                .perEstado(perfil.getPerEstado())
                .build();
    }

    public Perfil toEntity(PerfilCreateDTO dto) {
        return Perfil.builder()
                .nomPerfil(dto.getNomPerfil())
                .desPerfil(dto.getDesPerfil())
                .perEstado(dto.getPerEstado())
                .build();
    }}
