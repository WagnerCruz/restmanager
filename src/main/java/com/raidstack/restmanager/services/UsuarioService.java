package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.UsuarioDTO;
import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.mapper.UsuarioMapper;
import com.raidstack.restmanager.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDTO findById(long id) {

        UsuarioDTO usuarioDTO = null;
        Optional<Usuario> userById = this.usuarioRepository.findById(id);
        if (userById.isPresent()) {
            usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(userById.get());
        }
        return usuarioDTO;

    }
    public UsuarioDTO findByLogin(String login) {
        Optional<Usuario> userByLogin = this.usuarioRepository.findByLogin(login);
        return userByLogin.map(usuarioMapper::usuarioToUsuarioDTO).orElse(null);
    }

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = this.usuarioRepository.findAll(10, 1);
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            usuariosDTO.add(usuarioMapper.usuarioToUsuarioDTO(usuario));
        }
        return usuariosDTO;
    }

    public Integer criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
        Integer flagCriado = 0;
        if (validarUsuarioByCpfEmailLogin(usuarioDTO).isEmpty()) {
            flagCriado = this.usuarioRepository.criarUsuario(usuario);
        }
        if (flagCriado > 0) {
            Assert.state(flagCriado > 0, "Erro ao gravar usuario: " + usuario.getId());
        }
        return flagCriado;
    }

    public Integer atualizarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
        if(validarUsuarioByCpfEmailLogin(usuarioDTO).isEmpty()) {
            return this.usuarioRepository.atualizarUsuario(usuario);
        }else{
            return 0;
        }
    }

    public String deletarUsuario(UsuarioDTO usuarioDTO) {
        Optional<Long> id = this.usuarioRepository.findUsuarioByCPF(usuarioDTO.cpf()).map(Usuario::getId);
        Integer flagDeletado = this.usuarioRepository.deletarUsuario(id.get());
        if (flagDeletado > 0) {
            return "Usuario deletado com sucesso";
        }
        return "Erro ao deletar usuario";
    }

    public Integer atualizarSenha(UsuarioDTO usuario) {
        if (validarUsuario(usuario).isPresent()) {
            Usuario usuarioEntity = usuarioMapper.usuarioDTOToUsuario(usuario);
            return this.usuarioRepository.atualizarUsuario(usuarioEntity);
        }
        return 0;
    }

    private Optional<Usuario> validarUsuario(UsuarioDTO usuarioDTO) {
        return this.usuarioRepository.findUsuarioByCPF(usuarioDTO.cpf());
    }

    private List<UsuarioDTO> validarUsuarioByCpfEmailLogin(UsuarioDTO  usuarioDTO) {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        this.usuarioRepository.findUsuarioByCpfEmailLogin(usuarioDTO.cpf(), usuarioDTO.email(), usuarioDTO.login())
                .forEach(usuario -> usuariosDTO.add(usuarioMapper.usuarioToUsuarioDTO(usuario)));
        return usuariosDTO;
    }

}
