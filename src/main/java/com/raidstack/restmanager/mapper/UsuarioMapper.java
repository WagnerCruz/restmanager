package com.raidstack.restmanager.mapper;

import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioCriarDTO;
import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.vo.UsuarioVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioAtualizarDTO usuarioToUsuarioDTO(Usuario usuario);

    Usuario usuarioDTOToUsuario(UsuarioAtualizarDTO usuarioDTO);
    Usuario usuarioCriarDTOToUsuario(UsuarioCriarDTO usuarioDTO);

    UsuarioVO usuarioToUsuarioVO(Usuario usuario);

}
