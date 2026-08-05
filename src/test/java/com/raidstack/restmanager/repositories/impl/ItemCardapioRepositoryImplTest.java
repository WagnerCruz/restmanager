package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.entity.ItemCardapio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.raidstack.restmanager.helper.MocksHelper.mockItemCardapio;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemCardapioRepositoryImplTest {

    @Mock
    private JdbcClient jdbcClient;

    @Mock
    private JdbcClient.StatementSpec statementSpec;

    @Mock
    private JdbcClient.StatementSpec paramSpec;

    @Mock
    private JdbcClient.MappedQuerySpec querySpec;

    @InjectMocks
    private ItemCardapioRepositoryImpl repository;

    @Test
    void buscarItemPorIdRetornaListaComItemQuandoIdExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockItemCardapio()));

        List<ItemCardapio> result = repository.buscarItemPorId(1L);

        assertEquals(1, result.size());
        assertEquals("Pasta Carbonara", result.get(0).getNome_item());
    }

    @Test
    void buscarItemPorIdRetornaListaVaziaQuandoIdNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 999L)).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItemPorId(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarItemPorIdComIdZero() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 0L)).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItemPorId(0L);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarItemPorIdComIdNegativo() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", -1L)).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItemPorId(-1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarTodosItensRetornaListaComItensPaginados() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 10)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 0)).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockItemCardapio()));

        List<ItemCardapio> result = repository.buscarTodosItens(10, 0);

        assertEquals(1, result.size());
    }

    @Test
    void buscarTodosItensRetornaListaVaziaQuandoOffsetForaDoAlcance() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 10)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 1000)).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarTodosItens(10, 1000);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarTodosItensComTamanhoPequeno() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 1)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 0)).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockItemCardapio()));

        List<ItemCardapio> result = repository.buscarTodosItens(1, 0);

        assertEquals(1, result.size());
    }

    @Test
    void buscarTodosItensComTamanhoGrande() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 1000)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 0)).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockItemCardapio()));

        List<ItemCardapio> result = repository.buscarTodosItens(1000, 0);

        assertEquals(1, result.size());
    }

    @Test
    void buscarItemPorNomeRetornaListaComItemQuandoNomeExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Pasta Carbonara")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockItemCardapio()));

        List<ItemCardapio> result = repository.buscarItemPorNome("Pasta Carbonara");

        assertEquals(1, result.size());
        assertEquals("Pasta Carbonara", result.get(0).getNome_item());
    }

    @Test
    void buscarItemPorNomeRetornaListaVaziaQuandoNomeNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Inexistente")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItemPorNome("Inexistente");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarItemPorNomeComStringVazia() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItemPorNome("");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarItensPorDisponibilidadeRetornaListaComItensDisponiveis() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("disponibilidade", "Sim")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockItemCardapio()));

        List<ItemCardapio> result = repository.buscarItensPorDisponibilidade("Sim");

        assertEquals(1, result.size());
        assertEquals("Sim", result.get(0).getDisponibilidade());
    }

    @Test
    void buscarItensPorDisponibilidadeRetornaListaVaziaQuandoNenhumItemDisponivel() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("disponibilidade", "Não")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItensPorDisponibilidade("Não");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarItensPorDisponibilidadeComStringVazia() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("disponibilidade", "")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItensPorDisponibilidade("");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarItemPorDescricaoRetornaListaComItemQuandoDescricaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("descricao", "Massa com ovos e bacon")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockItemCardapio()));

        List<ItemCardapio> result = repository.buscarItemPorDescricao("Massa com ovos e bacon");

        assertEquals(1, result.size());
        assertEquals("Massa com ovos e bacon", result.get(0).getDescricao());
    }

    @Test
    void buscarItemPorDescricaoRetornaListaVaziaQuandoDescricaoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("descricao", "Inexistente")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItemPorDescricao("Inexistente");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarItemPorDescricaoComStringVazia() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("descricao", "")).thenReturn(paramSpec);
        when(paramSpec.query(ItemCardapio.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<ItemCardapio> result = repository.buscarItemPorDescricao("");

        assertTrue(result.isEmpty());
    }

    @Test
    void criarNovoItemCardapioRetornaUmQuandoInsercaoBemSucedida() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Pasta Carbonara")).thenReturn(paramSpec);
        when(paramSpec.param("descricao", "Massa com ovos e bacon")).thenReturn(paramSpec);
        when(paramSpec.param("preco", 35.50)).thenReturn(paramSpec);
        when(paramSpec.param("disponibilidade", "Sim")).thenReturn(paramSpec);
        when(paramSpec.param("imagem", "carbonara.jpg")).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.criarNovoItemCardapio(mockItemCardapio());

        assertEquals(1, result);
    }

    @Test
    void criarNovoItemCardapioRetornaZeroQuandoInsercaoFalha() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Pasta Carbonara")).thenReturn(paramSpec);
        when(paramSpec.param("descricao", "Massa com ovos e bacon")).thenReturn(paramSpec);
        when(paramSpec.param("preco", 35.50)).thenReturn(paramSpec);
        when(paramSpec.param("disponibilidade", "Sim")).thenReturn(paramSpec);
        when(paramSpec.param("imagem", "carbonara.jpg")).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.criarNovoItemCardapio(mockItemCardapio());

        assertEquals(0, result);
    }

    @Test
    void criarNovoItemCardapioComValoresNulosUsaParametrosCorretos() {
        ItemCardapio itemComNulos = new ItemCardapio();
        itemComNulos.setNome_item(null);
        itemComNulos.setDescricao(null);
        itemComNulos.setValor_item(null);
        itemComNulos.setDisponibilidade(null);
        itemComNulos.setImagem_prato(null);

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", null)).thenReturn(paramSpec);
        when(paramSpec.param("descricao", null)).thenReturn(paramSpec);
        when(paramSpec.param("preco", null)).thenReturn(paramSpec);
        when(paramSpec.param("disponibilidade", null)).thenReturn(paramSpec);
        when(paramSpec.param("imagem", null)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.criarNovoItemCardapio(itemComNulos);

        assertEquals(1, result);
    }

    @Test
    void criarNovoItemCardapioComDadosDiferentesComValoresAltos() {
        ItemCardapio novoItem = new ItemCardapio();
        novoItem.setNome_item("Peixe Grelhado");
        novoItem.setDescricao("Peixe fresco do dia");
        novoItem.setValor_item(150.00);
        novoItem.setDisponibilidade("Não");
        novoItem.setImagem_prato("peixe.jpg");

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Peixe Grelhado")).thenReturn(paramSpec);
        when(paramSpec.param("descricao", "Peixe fresco do dia")).thenReturn(paramSpec);
        when(paramSpec.param("preco", 150.00)).thenReturn(paramSpec);
        when(paramSpec.param("disponibilidade", "Não")).thenReturn(paramSpec);
        when(paramSpec.param("imagem", "peixe.jpg")).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.criarNovoItemCardapio(novoItem);

        assertEquals(1, result);
    }

    @Test
    void atualizarItemCardapioRetornaUmQuandoAtualizacaoBemSucedida() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.param("nome", "Pasta Carbonara")).thenReturn(paramSpec);
        when(paramSpec.param("descricao", "Massa com ovos e bacon")).thenReturn(paramSpec);
        when(paramSpec.param("preco", 35.50)).thenReturn(paramSpec);
        when(paramSpec.param("disponibilidade", "Sim")).thenReturn(paramSpec);
        when(paramSpec.param("imagem", "carbonara.jpg")).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.atualizarItemCardapio(mockItemCardapio());

        assertEquals(1, result);
    }

    @Test
    void atualizarItemCardapioRetornaZeroQuandoAtualizacaoFalha() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.param("nome", "Pasta Carbonara")).thenReturn(paramSpec);
        when(paramSpec.param("descricao", "Massa com ovos e bacon")).thenReturn(paramSpec);
        when(paramSpec.param("preco", 35.50)).thenReturn(paramSpec);
        when(paramSpec.param("disponibilidade", "Sim")).thenReturn(paramSpec);
        when(paramSpec.param("imagem", "carbonara.jpg")).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.atualizarItemCardapio(mockItemCardapio());

        assertEquals(0, result);
    }

    @Test
    void atualizarItemCardapioComIdDiferenteUsaIdCorreto() {
        ItemCardapio itemComIdDiferente = new ItemCardapio();
        itemComIdDiferente.setId(999L);
        itemComIdDiferente.setNome_item("Novo Item");
        itemComIdDiferente.setDescricao("Nova Descrição");
        itemComIdDiferente.setValor_item(50.00);
        itemComIdDiferente.setDisponibilidade("Sim");
        itemComIdDiferente.setImagem_prato("novo.jpg");

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 999L)).thenReturn(paramSpec);
        when(paramSpec.param("nome", "Novo Item")).thenReturn(paramSpec);
        when(paramSpec.param("descricao", "Nova Descrição")).thenReturn(paramSpec);
        when(paramSpec.param("preco", 50.00)).thenReturn(paramSpec);
        when(paramSpec.param("disponibilidade", "Sim")).thenReturn(paramSpec);
        when(paramSpec.param("imagem", "novo.jpg")).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.atualizarItemCardapio(itemComIdDiferente);

        assertEquals(1, result);
    }

    @Test
    void atualizarItemCardapioComValoresNulos() {
        ItemCardapio itemComNulos = new ItemCardapio();
        itemComNulos.setId(5L);
        itemComNulos.setNome_item(null);
        itemComNulos.setDescricao(null);
        itemComNulos.setValor_item(null);
        itemComNulos.setDisponibilidade(null);
        itemComNulos.setImagem_prato(null);

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 5L)).thenReturn(paramSpec);
        when(paramSpec.param("nome", null)).thenReturn(paramSpec);
        when(paramSpec.param("descricao", null)).thenReturn(paramSpec);
        when(paramSpec.param("preco", null)).thenReturn(paramSpec);
        when(paramSpec.param("disponibilidade", null)).thenReturn(paramSpec);
        when(paramSpec.param("imagem", null)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.atualizarItemCardapio(itemComNulos);

        assertEquals(1, result);
    }

    @Test
    void deletarItemCardapioRetornaUmQuandoDeletacaoBemSucedida() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.deletarItemCardapio(1L);

        assertEquals(1, result);
    }

    @Test
    void deletarItemCardapioRetornaZeroQuandoDeletacaoFalha() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 999L)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.deletarItemCardapio(999L);

        assertEquals(0, result);
    }

    @Test
    void deletarItemCardapioComIdNegativoRetornaZero() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", -1L)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.deletarItemCardapio(-1L);

        assertEquals(0, result);
    }

    @Test
    void deletarItemCardapioComIdZero() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 0L)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.deletarItemCardapio(0L);

        assertEquals(0, result);
    }
}

