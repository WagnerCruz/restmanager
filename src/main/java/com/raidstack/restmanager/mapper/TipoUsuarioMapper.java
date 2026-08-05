package com.raidstack.restmanager.mapper;


import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioBuscarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioCriarDTO;

import com.raidstack.restmanager.entity.TipoUsuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoUsuarioMapper {


    TipoUsuarioAtualizarDTO tipoUsuarioToTipoUsuarioAtualizarDTO(TipoUsuario tipoUsuario);
    TipoUsuarioBuscarDTO tipoUsuarioToTipoUsuarioBuscarDTO(TipoUsuario tipoUsuario);
    TipoUsuarioCriarDTO tipoUsuarioToTipoUsuarioCriarDTO(TipoUsuario tipoUsuario);

    TipoUsuario tipoUsuarioAtualizarDTOToTipoUsuario(TipoUsuarioAtualizarDTO tipoUsuarioAtualizarDTO);
    TipoUsuario tipoUsuarioCriarDTOToTipoUsuario(TipoUsuarioCriarDTO tipoUsuarioCriarDTO);
    TipoUsuario tipoUsuarioBuscarDTOToTipoUsuario(TipoUsuario tipoUsuario);
}
