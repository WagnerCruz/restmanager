package com.raidstack.restmanager.mapper;

import com.raidstack.restmanager.dtos.UsuarioDTO;
import com.raidstack.restmanager.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);

    Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO);

}
