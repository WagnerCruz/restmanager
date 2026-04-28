package com.raidstack.restmanager.repositories;

import com.raidstack.restmanager.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByLogin(String login);
    List<Usuario> findAll(int size, int offset);
    Integer criarUsuario(Usuario usuario);
    Integer atualizarUsuario(Usuario usuario);
    Integer deletarUsuario(Long id);
    Optional<Usuario> findUsuarioByCPF(String cpf);
    List<Usuario> findUsuarioByCpfEmailLogin(String cpf, String email, String login);

}
