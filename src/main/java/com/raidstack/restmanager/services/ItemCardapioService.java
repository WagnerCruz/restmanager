package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioAtualizarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioBuscarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioCriarDTO;
import com.raidstack.restmanager.entity.ItemCardapio;
import com.raidstack.restmanager.mapper.ItemCardapioMapper;
import com.raidstack.restmanager.repositories.ItemCardapioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCardapioService {

    private final ItemCardapioRepository itemCardapioRepository;
    private final ItemCardapioMapper itemCardapioMapper;

    public ItemCardapioService(ItemCardapioRepository itemCardapioRepository, ItemCardapioMapper itemCardapioMapper) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.itemCardapioMapper = itemCardapioMapper;
    }

    public List<ItemCardapioBuscarDTO> buscarItemPorNome(String nome) {
        return itemCardapioRepository.buscarItemPorNome(nome)
                .stream()
                .map(itemCardapioMapper::itemCardapioToItemCardapioBuscarDTO)
                .toList();
    }

    public List<ItemCardapioBuscarDTO> buscarItemPorDescricao(String descricao) {
        return itemCardapioRepository.buscarItemPorDescricao(descricao)
                .stream()
                .map(itemCardapioMapper::itemCardapioToItemCardapioBuscarDTO)
                .toList();
    }

    public List<ItemCardapioBuscarDTO> buscarItensPorDisponibilidade(String disponibilidade) {
        return itemCardapioRepository.buscarItensPorDisponibilidade(disponibilidade)
                .stream()
                .map(itemCardapioMapper::itemCardapioToItemCardapioBuscarDTO)
                .toList();
    }

    public List<ItemCardapioBuscarDTO> buscarTodosItens(int size, int offset) {
        return itemCardapioRepository.buscarTodosItens(size, offset)
                .stream()
                .map(itemCardapioMapper::itemCardapioToItemCardapioBuscarDTO)
                .toList();
    }

    public void cadastrarItemCardapio(ItemCardapioCriarDTO itemCardapioCriarDTO) {
        ItemCardapio itemCardapio = itemCardapioMapper.itemCardapioCriarDTOToItemCardapio(itemCardapioCriarDTO);
        itemCardapioRepository.criarNovoItemCardapio(itemCardapio);
    }

    public void atualizarItemCardapio(ItemCardapioAtualizarDTO itemCardapioAtualizarDTO) {
        ItemCardapio itemCardapio = itemCardapioMapper.itemCardapioAtualizarDTOToItemCardapio(itemCardapioAtualizarDTO);
        itemCardapioRepository.atualizarItemCardapio(itemCardapio);
    }

    public void deletarItemCardapio(ItemCardapioAtualizarDTO itemCardapioAtualizarDTO) {
        ItemCardapio itemCardapio = itemCardapioRepository.buscarItemPorId(itemCardapioAtualizarDTO.id())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item de cardápio não encontrado"));
        itemCardapioRepository.deletarItemCardapio(itemCardapio.getId());
    }

}
