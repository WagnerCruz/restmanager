package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.entity.Restaurante;
import com.raidstack.restmanager.repositories.RestauranteRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    private final JdbcClient jdbcClient;

    public RestauranteRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    @Override
    public List<Restaurante> buscarRestaurantePorNome(String nome) {
        return this.jdbcClient.sql("SELECT * FROM restaurantes WHERE nome = :nome")
                .param("nome", nome)
                .query(Restaurante.class)
                .list();
    }

    @Override
    public List<Restaurante> buscarTodosRestaurantes(int size, int offset) {
        return this.jdbcClient.sql("SELECT * FROM restaurantes LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(Restaurante.class)
                .list();
    }

    @Override
    public List<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha) {
        return this.jdbcClient.sql("SELECT * FROM restaurantes WHERE tipo_cozinha = :tipoCozinha")
                .param("tipoCozinha", tipoCozinha)
                .query(Restaurante.class)
                .list();
    }

    @Override
    public List<Restaurante> buscarPorHorario(Time horaInicio) {
        return this.jdbcClient.sql("SELECT * FROM restaurantes WHERE hora_inicio <= :horaInicio AND hora_fim >= :horaInicio")
                .param("horaInicio", horaInicio)
                .query(Restaurante.class)
                .list();
    }


    @Override
    public Integer cadastrarRestaurante(Restaurante restaurante) {
        return this.jdbcClient.sql("INSERT INTO restaurantes (nome, tipo_cozinha, hora_inicio, hora_fim, id_usuario) " +
                        "VALUES (:nome, :tipoCozinha, :horaInicio, :horaFim)")
                .param("nome", restaurante.getNome())
                .param("tipoCozinha", restaurante.getTipo_cozinha())
                .param("horaInicio", restaurante.getHorario_inicio_functo())
                .param("horaFim", restaurante.getHorario_fim_functo())
                .param("id_usuario", restaurante.getId_usuario())
                .update();
    }

    @Override
    public Integer atualizarRestaurante(Restaurante restaurante) {
        return this.jdbcClient.sql("UPDATE restaurantes SET nome = :nome, tipo_cozinha = :tipoCozinha, hora_inicio = :horaInicio, hora_fim = :horaFim, id_usuario =: idDono WHERE id = :id")
                .param("id", restaurante.getId())
                .param("nome", restaurante.getNome())
                .param("tipoCozinha", restaurante.getTipo_cozinha())
                .param("horaInicio", restaurante.getHorario_inicio_functo())
                .param("horaFim", restaurante.getHorario_fim_functo())
                .param("idDono", restaurante.getId_usuario())
                .update();
    }

    @Override
    public Integer deletarRestaurante(Long id) {
        return this.jdbcClient.sql("DELETE FROM restaurantes WHERE id = :id")
                .param("id", id)
                .update();
    }


    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return this.jdbcClient.sql("SELECT * FROM restaurantes WHERE id = :id")
                .param("id", id)
                .query(Restaurante.class)
                .optional();
    }

}
