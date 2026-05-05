package com.raidstack.restmanager.repositories;

import com.raidstack.restmanager.dtos.UsuarioSenhaDTO;
import com.raidstack.restmanager.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorLogin(String login);
    Optional<Usuario> buscarPorNome(String nome);
    List<Usuario> buscarTodos(int size, int offset);
    Integer criarUsuario(Usuario usuario);
    Integer atualizarUsuario(Usuario usuario);
    Integer atualizarSenhaUsuario(UsuarioSenhaDTO usuarioDTO);
    Integer deletarUsuario(Long id);
    Optional<Usuario> buscarUsuarioPorCPF(String cpf);
    List<Usuario> buscarUsuarioPorCpfEmailLogin(Usuario usuario);
    Optional<Usuario> validaUsuarioPorLoginESenha(UsuarioSenhaDTO usuarioDTO);

}
