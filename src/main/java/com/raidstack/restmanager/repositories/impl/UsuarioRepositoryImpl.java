package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.dtos.UsuarioSenhaDTO;
import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.repositories.UsuarioRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
        return this.jdbcClient.sql("INSERT INTO USUARIOS (nome, cpf, email, login, senha, endereco, numero, flag_proprietario, data_atualizacao) " +
                        "VALUES (:nome, :cpf, :email, :login, :senha, :endereco, :numero, :flag_proprietario, :data_atualizacao)")
                .param("nome", usuario.getNome())
                .param("cpf", usuario.getCpf())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("endereco", usuario.getEndereco())
                .param("numero", usuario.getNumero())
                .param("flag_proprietario", usuario.isFlagProprietario())
                .param("data_atualizacao", LocalDateTime.now())
                .update();
    }

    @Override
    public Integer atualizarUsuario(Usuario usuario) {
        return this.jdbcClient.sql("UPDATE USUARIOS SET nome = :nome, cpf = :cpf, email = :email, login = :login, " +
                        " endereco = :endereco, numero = :numero, flag_proprietario = :flag_proprietario, data_atualizacao = :data_atualizacao" +
                        " WHERE id = :id")
                .param("id", usuario.getId())
                .param("nome", usuario.getNome())
                .param("cpf", usuario.getCpf())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("endereco", usuario.getEndereco())
                .param("numero", usuario.getNumero())
                .param("flag_proprietario", usuario.isFlagProprietario())
                .param("data_atualizacao", LocalDateTime.now())
                .update();
    }

    @Override
    public Integer atualizarSenhaUsuario(UsuarioSenhaDTO usuarioDTO) {
        return this.jdbcClient.sql("UPDATE USUARIOS SET senha = :senha, data_atualizacao = :data_atualizacao WHERE id = :id")
                .param("id", usuarioDTO.id())
                .param("senha", usuarioDTO.senha())
                .param("data_atualizacao", LocalDateTime.now())
                .update();
    }

    @Override
    public Integer deletarUsuario(Long id) {
        return this.jdbcClient.sql("DELETE FROM USUARIOS WHERE id = :id")
                .param("id", id)
                .update();
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorCPF(String cpf) {
        return this.jdbcClient.sql("SELECT ID FROM USUARIOS WHERE CPF = :cpf")
                .param("cpf" ,cpf)
                .query(Usuario.class)
                .optional();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS WHERE id = :id")
                .param("id", id)
                .query(Usuario.class)
                .optional();
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS WHERE login = :login")
                .param("login", login)
                .query(Usuario.class)
                .optional();
    }

    @Override
    public Optional<Usuario> buscarPorNome(String nome) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS WHERE nome = :nome")
                .param("nome", nome)
                .query(Usuario.class)
                .optional();
    }

     @Override
    public List<Usuario> buscarTodos(int size, int offset) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset>0 ? offset-1 : offset)
                .query(Usuario.class)
                .list();
    }

    @Override
    public List<Usuario> buscarUsuarioPorCpfEmailLogin(Usuario usuario) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS WHERE cpf = :cpf OR email = :email OR login = :login")
                .param("cpf", usuario.getCpf())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .query(Usuario.class)
                .list();
    }

    @Override
    public Optional<Usuario> validaUsuarioPorLoginESenha(UsuarioSenhaDTO usuarioDTO) {
        return this.jdbcClient.sql("SELECT * FROM USUARIOS WHERE login = :login AND senha = :senha " +
                        " AND senha = :senha")
                .param("login", usuarioDTO.login())
                .param("senha", usuarioDTO.senha())
                .query(Usuario.class)
                .optional();
    }


}
