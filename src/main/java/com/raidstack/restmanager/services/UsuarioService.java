package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioCriarDTO;
import com.raidstack.restmanager.dtos.UsuarioSenhaDTO;
import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.mapper.UsuarioMapper;
import com.raidstack.restmanager.repositories.UsuarioRepository;
import com.raidstack.restmanager.vo.UsuarioVO;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioAtualizarDTO findById(long id) {

        UsuarioAtualizarDTO usuarioDTO = null;
        Optional<Usuario> userById = this.usuarioRepository.buscarPorId(id);
        if (userById.isPresent()) {
            usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(userById.get());
        }
        return usuarioDTO;

    }

    public UsuarioVO findByLogin(String login) {
        Optional<Usuario> userByLogin = this.usuarioRepository.buscarPorLogin(login);
        return userByLogin.map(usuarioMapper::usuarioToUsuarioVO).orElse(null);
    }

    public UsuarioVO findByNome(String nome) {
        Optional<Usuario> usuario = this.usuarioRepository.buscarPorNome(nome);
        return usuario.map(usuarioMapper::usuarioToUsuarioVO).orElse(null);
    }

    public List<UsuarioVO> findAll() {
        List<Usuario> usuarios = this.usuarioRepository.buscarTodos(10, 1);
        List<UsuarioVO> usuariosVO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            usuariosVO.add(usuarioMapper.usuarioToUsuarioVO(usuario));
        }
        return usuariosVO;
    }

    public Integer criarUsuario(UsuarioCriarDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.usuarioCriarDTOToUsuario(usuarioDTO);
        Integer flagCriado = 0;
        if (validarUsuarioPorCpfEmailLogin(usuario)) {
            flagCriado = this.usuarioRepository.criarUsuario(usuario);
        }
        if (flagCriado > 0) {
            Assert.state(flagCriado > 0, "Erro ao gravar usuario: " + usuario.getId());
        }
        return flagCriado;
    }

    public Integer atualizarUsuario(UsuarioAtualizarDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
        if(validarUsuarioPorCpfEmailLogin(usuario)) {
            return this.usuarioRepository.atualizarUsuario(usuario);
        }else{
            return 0;
        }
    }

    public String deletarUsuario(UsuarioAtualizarDTO usuarioDTO) {
        Optional<Long> id = this.usuarioRepository.buscarUsuarioPorCPF(usuarioDTO.cpf()).map(Usuario::getId);
        Integer flagDeletado = 0;
        if (id.isPresent()) {
            flagDeletado = this.usuarioRepository.deletarUsuario(id.get());
        } else {
            return "Erro Usuario não encontrado";
        }
        if (flagDeletado > 0) {
            return "Usuario deletado com sucesso";
        }
        return "Erro ao deletar usuario";
    }

    public Integer atualizarSenhaUsuario(UsuarioSenhaDTO usuarioDTO) {
        if (Objects.nonNull(usuarioDTO) && validarUsuarioPorCPF(usuarioDTO.cpf())) {
            return this.usuarioRepository.atualizarSenhaUsuario(usuarioDTO);
        }
        return 0;
    }

    private boolean validarUsuarioPorCPF(String CPF) {
        Optional<Usuario> usuario = this.usuarioRepository.buscarUsuarioPorCPF(CPF);
        return usuario.isPresent();
    }

    private boolean validarUsuarioPorCpfEmailLogin(Usuario usuario) {
        List<Usuario> usuarios = this.usuarioRepository
                .buscarUsuarioPorCpfEmailLoginDifferentID(usuario);
        return Optional.ofNullable(usuarios).orElse(new ArrayList<>()).isEmpty();
    }

    public boolean validarLoginUsuario(UsuarioSenhaDTO usuarioDTO) {
        Optional<Usuario> usuario = this.usuarioRepository.validaUsuarioPorLoginESenha(usuarioDTO);
        return usuario.isPresent();
    }

}
