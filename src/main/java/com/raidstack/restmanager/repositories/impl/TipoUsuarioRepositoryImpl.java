package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.entity.ItemCardapio;
import com.raidstack.restmanager.entity.TipoUsuario;
import com.raidstack.restmanager.repositories.TipoUsuarioRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class TipoUsuarioRepositoryImpl implements TipoUsuarioRepository {

    private final JdbcClient jdbcClient;

    public TipoUsuarioRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<TipoUsuario> buscarTipoUsuarioPorId(Long id) {
        return this.jdbcClient.sql("SELECT * FROM tipo_usuario WHERE id = :id")
                .param("id", id)
                .query(TipoUsuario.class)
                .list();
    }

    @Override
    public List<TipoUsuario> buscarTodosTipoUsuario(int size, int offset) {
        return this.jdbcClient.sql("SELECT * FROM tipo_usuario LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("TipoUsuario", offset > 0 ? offset-1 : offset)
                .query(TipoUsuario.class)
                .list();
    }

    @Override
    public List<TipoUsuario> buscarTipoUsuarioPorNome(String nome_tipo) {
        return this.jdbcClient.sql("SELECT * FROM tipo_usuario WHERE nome_tipo = :nome")
                .param("nome", nome_tipo)
                .query(TipoUsuario.class)
                .list();
    }

    @Override
    public Integer criarNovoTipoUsuario(TipoUsuario tipoUsuario) {
        return this.jdbcClient.sql("INSERT INTO tipo_usuario (nome_tipo) " +
                        "VALUES (:nome)")
                .param("nome", tipoUsuario.getNome_tipo())
                .update();
    }

    @Override
    public Integer atualizarTipoUsuario(TipoUsuario tipoUsuario) {
        return this.jdbcClient.sql("UPDATE tipo_usuario SET nome_tipo= :nome WHERE id = :id")
                .param("id", tipoUsuario.getId())
                .param("nome", tipoUsuario.getNome_tipo())
                .update();
    }

    @Override
    public Integer deletarTipoUsuario(Long id) {
        return this.jdbcClient.sql("DELETE FROM tipo_usuario WHERE id = :id")
                .param("id", id)
                .update();
    }
}
