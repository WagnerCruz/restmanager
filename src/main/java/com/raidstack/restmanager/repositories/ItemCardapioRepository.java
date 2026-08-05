package com.raidstack.restmanager.repositories;


import com.raidstack.restmanager.entity.ItemCardapio;

import java.util.List;

public interface ItemCardapioRepository {

    List<ItemCardapio> buscarItemPorId(Long id);
    List<ItemCardapio> buscarTodosItens(int size, int offset);
    List<ItemCardapio> buscarItemPorNome(String nome);
    List<ItemCardapio> buscarItensPorDisponibilidade(String disponibilidade);
    List<ItemCardapio> buscarItemPorDescricao(String descricao);
    Integer criarNovoItemCardapio(ItemCardapio itemCardapio);
    Integer atualizarItemCardapio(ItemCardapio itemCardapio);
    Integer deletarItemCardapio(Long id);
}
