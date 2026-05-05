package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioCriarDTO;
import com.raidstack.restmanager.dtos.UsuarioSenhaDTO;
import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.mapper.UsuarioMapper;
import com.raidstack.restmanager.repositories.UsuarioRepository;
import com.raidstack.restmanager.services.exceptions.ResourceBadRequestException;
import com.raidstack.restmanager.services.exceptions.ResourceExceptionDefault;
import com.raidstack.restmanager.services.exceptions.ResourceNotFoundException;
import com.raidstack.restmanager.vo.UsuarioVO;
import org.springframework.stereotype.Service;

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
        Optional<Usuario> userById = this.usuarioRepository.buscarPorId(id);
        if (userById.isEmpty()) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        return usuarioMapper.usuarioToUsuarioDTO(userById.get());
    }

    public UsuarioVO findByLogin(String login) {
        Optional<Usuario> userByLogin = this.usuarioRepository.buscarPorLogin(login);
        return userByLogin.map(usuarioMapper::usuarioToUsuarioVO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public UsuarioVO findByNome(String nome) {
        Optional<Usuario> usuario = this.usuarioRepository.buscarPorNome(nome);
        return usuario.map(usuarioMapper::usuarioToUsuarioVO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public List<UsuarioVO> findAll() {
        List<Usuario> usuarios = this.usuarioRepository.buscarTodos(10, 1);
        List<UsuarioVO> usuariosVO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            usuariosVO.add(usuarioMapper.usuarioToUsuarioVO(usuario));
        }
        return usuariosVO;
    }

    public void criarUsuario(UsuarioCriarDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.usuarioCriarDTOToUsuario(usuarioDTO);
        validarUsuarioPorCpfEmailLogin(usuario);
        Integer flagCriado = this.usuarioRepository.criarUsuario(usuario);
        if (flagCriado <= 0) {
            throw new ResourceExceptionDefault("Erro ao gravar Usuário");
        }
    }

    public void atualizarUsuario(UsuarioAtualizarDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
        validarUsuarioPorCpfEmailLogin(usuario);
        Integer flagAtualizado = this.usuarioRepository.atualizarUsuario(usuario);
        if (flagAtualizado <= 0) {
            throw new ResourceExceptionDefault("Erro ao atualizar Usuário ID["+usuario.getId()+"]");
        }
    }

    public void deletarUsuario(UsuarioAtualizarDTO usuarioDTO) {
        Optional<Long> id = this.usuarioRepository.buscarUsuarioPorCPF(usuarioDTO.cpf()).map(Usuario::getId);
        if (id.isEmpty()) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        Integer flagDeletado = this.usuarioRepository.deletarUsuario(id.get());
        if (flagDeletado <= 0) {
            throw new ResourceExceptionDefault("Erro ao deletar Usuário ID["+id.get()+"]");
        }
    }

    public void atualizarSenhaUsuario(UsuarioSenhaDTO usuarioDTO) {
        if (Objects.isNull(usuarioDTO)) {
            throw new ResourceExceptionDefault("Usuário informado é inválido");
        }
        validarUsuarioPorCPF(usuarioDTO.cpf());
        this.usuarioRepository.atualizarSenhaUsuario(usuarioDTO);
    }

    private void validarUsuarioPorCPF(String cpf) {
        Optional<Usuario> usuario = this.usuarioRepository.buscarUsuarioPorCPF(cpf);
        if (usuario.isEmpty()) {
            throw new ResourceBadRequestException("Erro ao validar Usuário: Nenhum usuário cadastrado com o CPF ["+cpf+"]");
        }
    }

    private void validarUsuarioPorCpfEmailLogin(Usuario usuario) {
        List<Usuario> usuarios = this.usuarioRepository.buscarUsuarioPorCpfEmailLogin(usuario);
        Usuario usuarioId = usuarios.stream()
                .filter(user -> user.getId().equals(usuario.getId()))
                .findAny().orElse(null);
        usuarios.remove(usuarioId);
        if (!usuarios.isEmpty()) {
            throw new ResourceBadRequestException("Erro ao validar Usuário: já existe um usuário com essas " +
                    "credenciais de cadastro CPF, Email ou Login");
        }
    }

    public void validarLoginUsuario(UsuarioSenhaDTO usuarioDTO) {
        Optional<Usuario> usuario = this.usuarioRepository.validaUsuarioPorLoginESenha(usuarioDTO);
        if (usuario.isPresent()) {
            throw new ResourceBadRequestException("Erro ao validar Usuário: Login e Senha estão incorretos");
        }
    }

}
