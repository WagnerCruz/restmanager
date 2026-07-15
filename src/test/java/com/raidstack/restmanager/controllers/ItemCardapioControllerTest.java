package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioAtualizarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioBuscarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioCriarDTO;
import com.raidstack.restmanager.services.ItemCardapioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioControllerTest {

    private ItemCardapioController controller;

    @Mock
    private ItemCardapioService service;

    @BeforeEach
    void setUp() {
        controller = new ItemCardapioController(service);
    }

    @Test
    void pesquisarTodosItensCardapioRetornaOkComLista() {
        ItemCardapioBuscarDTO dto1 = criarBuscarDTO("Pizza", "Pizza deliciosa", 35.0);
        ItemCardapioBuscarDTO dto2 = criarBuscarDTO("Pastel", "Pastel crocante", 6.0);
        List<ItemCardapioBuscarDTO> lista = Arrays.asList(dto1, dto2);

        when(service.buscarTodosItens(10, 1)).thenReturn(lista);

        ResponseEntity<List<ItemCardapioBuscarDTO>> response = controller.pesquisarTodosItensCardapio();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).buscarTodosItens(10, 1);
    }

    @Test
    void pesquisarItensPorNomeRetornaOkComLista() {
        ItemCardapioBuscarDTO dto = criarBuscarDTO("Pizza", "Pizza deliciosa", 35.0);
        when(service.buscarItemPorNome(dto.nome_item())).thenReturn(Arrays.asList(dto));

        ResponseEntity<List<ItemCardapioBuscarDTO>> response = controller.pesquisarItensPorNome(dto.nome_item());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).buscarItemPorNome(dto.nome_item());
    }

    @Test
    void pesquisarItensPorNomeQuandoServicoLancaExcecaoPropagaAExcecao() {
        ItemCardapioBuscarDTO dto = criarBuscarDTO("Erro", "Erro", 0.0);
        when(service.buscarItemPorNome(dto.nome_item())).thenThrow(new RuntimeException("erro"));

        assertThrows(RuntimeException.class, () -> controller.pesquisarItensPorNome(dto.nome_item()));
        verify(service, times(1)).buscarItemPorNome(dto.nome_item());
    }

    @Test
    void pesquisarItensPorDescricaoRetornaOkComLista() {
        ItemCardapioBuscarDTO dto = criarBuscarDTO("Pizza", "Pizza deliciosa", 35.0);
        when(service.buscarItemPorDescricao(dto.descricao())).thenReturn(Arrays.asList(dto));

        ResponseEntity<List<ItemCardapioBuscarDTO>> response = controller.pesquisarItensPorDescricao(dto.descricao());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).buscarItemPorDescricao(dto.descricao());
    }

    @Test
    void pesquisarItensPorDescricaoQuandoServicoLancaExcecaoPropagaAExcecao() {
        String descricao = "Erro";
        when(service.buscarItemPorDescricao(descricao)).thenThrow(new RuntimeException("erro"));

        assertThrows(RuntimeException.class, () -> controller.pesquisarItensPorDescricao(descricao));
        verify(service, times(1)).buscarItemPorDescricao(descricao);
    }

    @Test
    void pesquisarItensPorDisponibilidadeRetornaOkComLista() {
        String disponibilidade = "Disponível";
        ItemCardapioBuscarDTO dtoPizza1 = criarBuscarDTO("Pizza", "Pizza deliciosa", 35.0);
        ItemCardapioBuscarDTO dtoPizza2 = criarBuscarDTO("Pizza", "Pizza Gourmet", 65.0);
        when(service.buscarItensPorDisponibilidade(disponibilidade)).thenReturn(Arrays.asList(dtoPizza1, dtoPizza2));

        ResponseEntity<List<ItemCardapioBuscarDTO>> response = controller.pesquisarItensPorDisponibilidade(disponibilidade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).buscarItensPorDisponibilidade(disponibilidade);
    }

    @Test
    void pesquisarItensPorDisponbilidadeQuandoServicoLancaExcecaoPropagaAExcecao() {
        String disponibilidade = "Disponível";
        when(service.buscarItensPorDisponibilidade(disponibilidade)).thenThrow(new RuntimeException("erro"));

        assertThrows(RuntimeException.class, () -> controller.pesquisarItensPorDisponibilidade(disponibilidade));
        verify(service, times(1)).buscarItensPorDisponibilidade(disponibilidade);
    }

    @Test
    void cadastrarItemCardapioRetornaCreated() {
        ItemCardapioCriarDTO criarDTO = criarCriarDTO("Pizza Nova", "Deliciosa", 30.0, "Disponível", "img.jpg");

        ResponseEntity<Void> response = controller.cadastrarItemCardapio(criarDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service, times(1)).cadastrarItemCardapio(criarDTO);
    }

    @Test
    void atualizarItemCardapioRetornaOk() {
        ItemCardapioAtualizarDTO atualizarDTO = criarAtualizarDTO(1L, "Pizza", "Deliciosa", 30.0, "Disponível", "img.jpg");

        ResponseEntity<Void> response = controller.atualizarItemCardapio(atualizarDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).atualizarItemCardapio(atualizarDTO);
    }

    @Test
    void deletarItemCardapioRetornaOkQuandoSucesso() {
        ItemCardapioAtualizarDTO atualizarDTO = criarAtualizarDTO(1L, "Pizza", "Deliciosa", 30.0, "Disponível", "img.jpg");

        ResponseEntity<Void> response = controller.deletarItemCardapio(atualizarDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deletarItemCardapio(atualizarDTO);
    }

    @Test
    void deletarItemCardapioPropagaExcecaoQuandoServicoLanca() {
        ItemCardapioAtualizarDTO atualizarDTO = criarAtualizarDTO(999L, "Inexistente", "", 0.0, "", "");
        doThrow(new RuntimeException("not found")).when(service).deletarItemCardapio(atualizarDTO);

        assertThrows(RuntimeException.class, () -> controller.deletarItemCardapio(atualizarDTO));
        verify(service, times(1)).deletarItemCardapio(atualizarDTO);
    }

    private ItemCardapioBuscarDTO criarBuscarDTO(String nome, String descricao, Double valor) {
        return new ItemCardapioBuscarDTO(null, nome, descricao, valor);
    }

    private ItemCardapioCriarDTO criarCriarDTO(String nome, String descricao, Double valor, String disponibilidade, String imagem) {
        return new ItemCardapioCriarDTO(nome, descricao, valor, disponibilidade, imagem);
    }

    private ItemCardapioAtualizarDTO criarAtualizarDTO(Long id, String nome, String descricao, Double valor, String disponibilidade, String imagem) {
        return new ItemCardapioAtualizarDTO(id, nome, descricao, valor, disponibilidade, imagem);
    }

}

