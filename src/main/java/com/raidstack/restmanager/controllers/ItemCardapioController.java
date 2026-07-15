package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioAtualizarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioBuscarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioCriarDTO;
import com.raidstack.restmanager.services.ItemCardapioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/itemcardapio")
public class ItemCardapioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemCardapioController.class);
    private ItemCardapioService itemCardapioService;

    public ItemCardapioController(ItemCardapioService itemCardapioService) {
        this.itemCardapioService = itemCardapioService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ItemCardapioBuscarDTO>> pesquisarTodosItensCardapio() {
        LOGGER.info("Retorna uma lista de todos os itens de cardápio cadastrados");
        List<ItemCardapioBuscarDTO> itensCardapioBuscarDTO = itemCardapioService.buscarTodosItens(10,1);
        LOGGER.info("Foram encontrados {} itens de cardápio", itensCardapioBuscarDTO.size());
        return ResponseEntity.ok(itensCardapioBuscarDTO);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<ItemCardapioBuscarDTO>> pesquisarItensPorNome(@RequestParam String nome) {
        LOGGER.info("Pesquisando itens de cardápio pelo nome: {}", nome);
        List<ItemCardapioBuscarDTO> itensCardapioBuscarDTO = itemCardapioService.buscarItemPorNome(nome);
        LOGGER.info("Foram encontrados {} itens de cardápio com o nome informado", itensCardapioBuscarDTO.size());
        return ResponseEntity.ok(itensCardapioBuscarDTO);
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<ItemCardapioBuscarDTO>> pesquisarItensPorDescricao(@RequestParam String descricao) {
        LOGGER.info("Pesquisando itens de cardápio pela descrição: {}", descricao);
        List<ItemCardapioBuscarDTO> itensCardapioBuscarDTO = itemCardapioService.buscarItemPorDescricao(descricao);
        LOGGER.info("Foram encontrados {} itens de cardápio com a descrição informada", itensCardapioBuscarDTO.size());
        return ResponseEntity.ok(itensCardapioBuscarDTO);
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<ItemCardapioBuscarDTO>> pesquisarItensPorDisponibilidade(@RequestParam String disponibilidade) {
        LOGGER.info("Pesquisando itens de cardápio pela disponibilidade: {}", disponibilidade);
        List<ItemCardapioBuscarDTO> itensCardapioBuscarDTO = itemCardapioService.buscarItensPorDisponibilidade(disponibilidade);
        LOGGER.info("Foram encontrados {} itens de cardápio com a disponibilidade informada", itensCardapioBuscarDTO.size());
        return ResponseEntity.ok(itensCardapioBuscarDTO);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarItemCardapio(@Valid @RequestBody ItemCardapioCriarDTO itemCardapioDTO) {
        LOGGER.info("Cadastrando novo item de cardápio: {}", itemCardapioDTO.nome_item());
        itemCardapioService.cadastrarItemCardapio(itemCardapioDTO);
        LOGGER.info("Item de cardápio cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarItemCardapio(@Valid @RequestBody ItemCardapioAtualizarDTO itemCardapioAtualizarDTO) {
        LOGGER.info("Atualizando item de cardápio: {}", itemCardapioAtualizarDTO.nome_item());
        itemCardapioService.atualizarItemCardapio(itemCardapioAtualizarDTO);
        LOGGER.info("Item de cardápio atualizado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarItemCardapio(@Valid @RequestBody ItemCardapioAtualizarDTO itemCardapioAtualizarDTO) {
        LOGGER.info("Deletando item de cardápio: {}", itemCardapioAtualizarDTO.nome_item());
        itemCardapioService.deletarItemCardapio(itemCardapioAtualizarDTO);
        LOGGER.info("Item de cardápio deletado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
