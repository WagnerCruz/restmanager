package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioCriarDTO;
import com.raidstack.restmanager.dtos.UsuarioSenhaDTO;
import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.mapper.UsuarioMapper;
import com.raidstack.restmanager.repositories.UsuarioRepository;
import com.raidstack.restmanager.services.exceptions.ResourceBadRequestException;
import com.raidstack.restmanager.services.exceptions.ResourceConflictException;
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

    public List<UsuarioVO> findByNome(String nome) {
        List<Usuario> usuarios = this.usuarioRepository.buscarPorNome(nome.toLowerCase());
        if (usuarios.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário localizado");
        }
        List<UsuarioVO> usuariosVO = new ArrayList<>();
        usuarios.forEach(usuario -> usuariosVO.add(usuarioMapper.usuarioToUsuarioVO(usuario)));
        return usuariosVO;
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
        validarCredenciaisUsuario(usuario);
        Integer flagCriado = this.usuarioRepository.criarUsuario(usuario);
        if (flagCriado <= 0) {
            throw new ResourceExceptionDefault("Erro ao gravar Usuário");
        }
    }

    public void atualizarUsuario(UsuarioAtualizarDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
        validarCredenciaisUsuario(usuario);
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
            throw new ResourceBadRequestException("Usuário informado é inválido");
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

    private void validarCredenciaisUsuario(Usuario usuario) {
        List<String> errosValidacao = new ArrayList<>();
        Optional<Usuario> usuarioCPF = this.usuarioRepository.buscarUsuarioPorCPF(usuario.getCpf());
        Optional<Usuario> usuarioLogin = this.usuarioRepository.buscarPorLogin(usuario.getLogin());
        Optional<Usuario> usuarioEmail = this.usuarioRepository.buscarPorEmail(usuario.getEmail());

        if (usuarioCPF.isPresent() && !usuarioCPF.get().getId().equals(usuario.getId())) {
            errosValidacao.add("cpf: CPF informado já está cadastrado");
        }
        if (usuarioLogin.isPresent() && !usuarioLogin.get().getId().equals(usuario.getId())) {
            errosValidacao.add("login: Login informado já está cadastrado");
        }
        if (usuarioEmail.isPresent() && !usuarioEmail.get().getId().equals(usuario.getId())) {
            errosValidacao.add("email: E-mail informado já está cadastrado");
        }

        if (!errosValidacao.isEmpty()) {
            throw new ResourceConflictException("Erro ao validar Usuário: Algumas credenciais já estão cadastradas", errosValidacao);
        }
    }

    public void validarLoginUsuario(UsuarioSenhaDTO usuarioDTO) {
        Optional<Usuario> usuario = this.usuarioRepository.validaUsuarioPorLoginESenha(usuarioDTO);
        if (usuario.isEmpty()) {
            throw new ResourceBadRequestException("Erro ao validar Usuário: Login e Senha estão incorretos");
        }
    }

}
