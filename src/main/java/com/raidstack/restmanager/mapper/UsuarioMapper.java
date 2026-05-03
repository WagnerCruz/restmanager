package com.raidstack.restmanager.mapper;

import com.raidstack.restmanager.dtos.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioCriarDTO;
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
