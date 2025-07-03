package com.utec.pinfranow.mapper;

import com.utec.pinfranow.dto.AccesoFuncionalidadCreateDTO;
import com.utec.pinfranow.dto.AccesoFuncionalidadDTO;
import com.utec.pinfranow.dto.FuncionalidadDTO;
import com.utec.pinfranow.model.AccesoFuncionalidad;
import com.utec.pinfranow.model.Funcionalidad;
import com.utec.pinfranow.model.Perfil;
import com.utec.pinfranow.model.ids.AccesoFuncionalidadId;
import org.springframework.stereotype.Component;

@Component
public class AccesoFuncionalidadMapper {

    public AccesoFuncionalidadDTO toDto(AccesoFuncionalidad entity) {
        if (entity == null) return null;

        return AccesoFuncionalidadDTO.builder()
                .idPerfil(entity.getPerfil().getIdPerfil())
                .idFuncionalidad(entity.getFuncionalidad().getId())
                .build();
    }

    public AccesoFuncionalidad toEntity(AccesoFuncionalidadCreateDTO dto, Perfil perfil, Funcionalidad funcionalidad) {
        if (dto == null) return null;

        return AccesoFuncionalidad.builder()
                .id(new AccesoFuncionalidadId(dto.getIdPerfil(), dto.getIdFuncionalidad()))
                .perfil(perfil)
                .funcionalidad(funcionalidad)
                .build();
    }
}
