package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.entity.ItemCardapio;
import com.raidstack.restmanager.repositories.ItemCardapioRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemCardapioRepositoryImpl implements ItemCardapioRepository {

    private final JdbcClient jdbcClient;

    public ItemCardapioRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<ItemCardapio> buscarItemPorId(Long id) {
        return this.jdbcClient.sql("SELECT * FROM item_cardapio WHERE id = :id")
                .param("id", id)
                .query(ItemCardapio.class)
                .list();
    }

    @Override
    public List<ItemCardapio> buscarTodosItens(int size, int offset) {
        return this.jdbcClient.sql("SELECT * FROM item_cardapio LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset > 0 ? offset-1 : offset)
                .query(ItemCardapio.class)
                .list();
    }

    @Override
    public List<ItemCardapio> buscarItemPorNome(String nome) {
        return this.jdbcClient.sql("SELECT * FROM item_cardapio WHERE nome = :nome")
                .param("nome", nome)
                .query(ItemCardapio.class)
                .list();
    }

    @Override
    public List<ItemCardapio> buscarItensPorDisponibilidade(String disponibilidade) {
        return this.jdbcClient.sql("SELECT * FROM item_cardapio WHERE disponibilidade = :disponibilidade")
                .param("disponibilidade", disponibilidade)
                .query(ItemCardapio.class)
                .list();
    }

    @Override
    public List<ItemCardapio> buscarItemPorDescricao(String descricao) {
        return this.jdbcClient.sql("SELECT * FROM item_cardapio WHERE descricao = :descricao")
                .param("descricao", descricao)
                .query(ItemCardapio.class)
                .list();
    }

    @Override
    public Integer criarNovoItemCardapio(ItemCardapio itemCardapio) {
        return this.jdbcClient.sql("INSERT INTO item_cardapio (nome_item, descricao, valor_item, disponibilidade, imagem_prato) " +
                        "VALUES (:nome, :descricao, :preco, :disponibilidade, :imagem)")
                .param("nome", itemCardapio.getNome_item())
                .param("descricao", itemCardapio.getDescricao())
                .param("preco", itemCardapio.getValor_item())
                .param("disponibilidade", itemCardapio.getDisponibilidade())
                .param("imagem", itemCardapio.getImagem_prato())
                .update();
    }

    @Override
    public Integer atualizarItemCardapio(ItemCardapio itemCardapio) {
        return this.jdbcClient.sql("UPDATE item_cardapio SET nome_item = :nome, descricao = :descricao, valor_item = :preco, disponibilidade = :disponibilidade, imagem_prato = :imagem WHERE id = :id")
                .param("id", itemCardapio.getId())
                .param("nome", itemCardapio.getNome_item())
                .param("descricao", itemCardapio.getDescricao())
                .param("preco", itemCardapio.getValor_item())
                .param("disponibilidade", itemCardapio.getDisponibilidade())
                .param("imagem", itemCardapio.getImagem_prato())
                .update();
    }

    @Override
    public Integer deletarItemCardapio(Long id) {
        return this.jdbcClient.sql("DELETE FROM item_cardapio WHERE id = :id")
                .param("id", id)
                .update();
    }

}
