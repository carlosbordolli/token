package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.AuxAdministrativoDTO;
import com.utec.pinfranow.model.AuxAdministrativo;
import com.utec.pinfranow.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AuxAdministrativoMapper {

    public AuxAdministrativoDTO toDto(AuxAdministrativo entity) {
        if (entity == null) return null;

        return AuxAdministrativoDTO.builder()
                .id(entity.getId())
                .idUsuario(entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null)
                .build();
    }

    public static AuxAdministrativo toEntity(AuxAdministrativoDTO dto, Usuario usuario) {
        if (dto == null) return null;

        return AuxAdministrativo.builder()
                .id(dto.getId())
                .usuario(usuario)
                .build();
    }
}
