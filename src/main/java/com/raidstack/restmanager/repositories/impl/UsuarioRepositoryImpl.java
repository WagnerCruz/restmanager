package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.repositories.UsuarioRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository{

    private final JdbcClient jdbcClient;

    public UsuarioRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Integer criarUsuario(Usuario usuario) {
        return this.jdbcClient.sql("INSERT INTO USUARIOS (nome, cpf, email, login, senha, endereco, numero) " +
                        "VALUES (:nome, :cpf, :email, :login, :senha, :endereco, :numero)")
                .param("nome", usuario.getNome())
                .param("cpf", usuario.getCpf())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("endereco", usuario.getEndereco())
                .param("numero", usuario.getNumero())
                .update();
    }

    @Override
    public Integer atualizarUsuario(Usuario usuario) {
        return this.jdbcClient.sql("UPDATE USUARIOS SET nome = :nome, cpf = :cpf, email = :email, login = :login, senha = :senha, endereco = :endereco, numero = :numero WHERE id = :id")
                .param("id", usuario.getId())
                .param("nome", usuario.getNome())
                .param("cpf", usuario.getCpf())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("endereco", usuario.getEndereco())
                .param("numero", usuario.getNumero())
                .update();
    }

    @Override
    public Integer deletarUsuario(Long id) {
        return this.jdbcClient.sql("DELETE FROM USUARIOS WHERE id = :id")
                .param("id", id)
                .update();
    }

    @Override
    public Optional<Usuario> findUsuarioByCPF(String cpf) {
        return this.jdbcClient.sql("SELECT ID FROM USUARIOS WHERE CPF = :cpf")
                .param("cpf" ,cpf)
                .query(Usuario.class)
                .optional();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS WHERE id = :id")
                .param("id", id)
                .query(Usuario.class)
                .optional();
    }

     @Override
    public Optional<Usuario> findByLogin(String login) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS WHERE login = :login")
                .param("login", login)
                .query(Usuario.class)
                .optional();
    }

     @Override
    public List<Usuario> findAll(int size, int offset) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(Usuario.class)
                .list();
    }

    @Override
    public List<Usuario> findUsuarioByCpfEmailLogin(String cpf, String email, String login) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS WHERE cpf = :cpf OR email = :email OR login = :login")
                .param("cpf", cpf)
                .param("email", email)
                .param("login", login)
                .query(Usuario.class)
                .list();
    }


}
