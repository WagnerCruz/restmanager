package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioAtualizarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioBuscarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioCriarDTO;
import com.raidstack.restmanager.entity.ItemCardapio;
import com.raidstack.restmanager.mapper.ItemCardapioMapper;
import com.raidstack.restmanager.repositories.ItemCardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioServiceTest {

    private ItemCardapioService itemCardapioService;

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @Mock
    private ItemCardapioMapper itemCardapioMapper;

    @BeforeEach
    void setUp() {
        itemCardapioService = new ItemCardapioService(itemCardapioRepository, itemCardapioMapper);
    }

    @Test
    void buscarItemPorNomeRetornaListaDTOQuandoNomeExiste() {
        String nome = "Pizza";
        ItemCardapio item1 = criarItemCardapio(1L, "Pizza Margherita", "Pizza clássica", 35.0, "Disponível", "pizza.jpg");
        ItemCardapio item2 = criarItemCardapio(2L, "Pizza Quatro Queijos", "Pizza premium", 45.0, "Disponível", "pizza2.jpg");
        List<ItemCardapio> itens = Arrays.asList(item1, item2);

        ItemCardapioBuscarDTO dto1 = criarItemCardapioBuscarDTO("Pizza Margherita", "Pizza clássica", 35.0);
        ItemCardapioBuscarDTO dto2 = criarItemCardapioBuscarDTO("Pizza Quatro Queijos", "Pizza premium", 45.0);

        when(itemCardapioRepository.buscarItemPorNome(nome)).thenReturn(itens);
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item1)).thenReturn(dto1);
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item2)).thenReturn(dto2);

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItemPorNome(nome);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(dto1, resultado.get(0));
        assertEquals(dto2, resultado.get(1));
        verify(itemCardapioRepository, times(1)).buscarItemPorNome(nome);
        verify(itemCardapioMapper, times(2)).itemCardapioToItemCardapioBuscarDTO(any(ItemCardapio.class));
    }

    @Test
    void buscarItemPorNomeRetornaListaVaziaQuandoNomeNaoExiste() {
        String nome = "Inexistente";
        when(itemCardapioRepository.buscarItemPorNome(nome)).thenReturn(Collections.emptyList());

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItemPorNome(nome);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(itemCardapioRepository, times(1)).buscarItemPorNome(nome);
        verify(itemCardapioMapper, never()).itemCardapioToItemCardapioBuscarDTO(any());
    }

    @Test
    void buscarItemPorNomeComNomeVazioRetornaListaVazia() {
        String nome = "";
        when(itemCardapioRepository.buscarItemPorNome(nome)).thenReturn(Collections.emptyList());

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItemPorNome(nome);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void buscarItemPorDescricaoRetornaListaDTOQuandoDescricaoExiste() {
        String descricao = "Salgado";
        ItemCardapio item1 = criarItemCardapio(1L, "Coxinha", "Salgado delicioso", 5.0, "Disponível", "coxinha.jpg");
        ItemCardapio item2 = criarItemCardapio(2L, "Pastel", "Salgado crocante", 6.0, "Disponível", "pastel.jpg");
        List<ItemCardapio> itens = Arrays.asList(item1, item2);

        ItemCardapioBuscarDTO dto1 = criarItemCardapioBuscarDTO("Coxinha", "Salgado delicioso", 5.0);
        ItemCardapioBuscarDTO dto2 = criarItemCardapioBuscarDTO("Pastel", "Salgado crocante", 6.0);

        when(itemCardapioRepository.buscarItemPorDescricao(descricao)).thenReturn(itens);
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item1)).thenReturn(dto1);
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item2)).thenReturn(dto2);

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItemPorDescricao(descricao);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(itemCardapioRepository, times(1)).buscarItemPorDescricao(descricao);
        verify(itemCardapioMapper, times(2)).itemCardapioToItemCardapioBuscarDTO(any(ItemCardapio.class));
    }

    @Test
    void buscarItemPorDescricaoRetornaListaVaziaQuandoDescricaoNaoExiste() {
        String descricao = "Inexistente";
        when(itemCardapioRepository.buscarItemPorDescricao(descricao)).thenReturn(Collections.emptyList());

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItemPorDescricao(descricao);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(itemCardapioRepository, times(1)).buscarItemPorDescricao(descricao);
    }

    @Test
    void buscarItemPorDescricaoComDescricaoVaziaRetornaListaVazia() {
        String descricao = "";
        when(itemCardapioRepository.buscarItemPorDescricao(descricao)).thenReturn(Collections.emptyList());

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItemPorDescricao(descricao);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void buscarItensPorDisponibilidadeRetornaListaDTOQuandoDisponibilidadeExiste() {
        String disponibilidade = "Disponível";
        ItemCardapio item1 = criarItemCardapio(1L, "Pizza", "Pizza deliciosa", 35.0, "Disponível", "pizza.jpg");
        ItemCardapio item2 = criarItemCardapio(2L, "Pastel", "Pastel crocante", 6.0, "Disponível", "pastel.jpg");
        List<ItemCardapio> itens = Arrays.asList(item1, item2);

        ItemCardapioBuscarDTO dto1 = criarItemCardapioBuscarDTO("Pizza", "Pizza deliciosa", 35.0);
        ItemCardapioBuscarDTO dto2 = criarItemCardapioBuscarDTO("Pastel", "Pastel crocante", 6.0);

        when(itemCardapioRepository.buscarItensPorDisponibilidade(disponibilidade)).thenReturn(itens);
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item1)).thenReturn(dto1);
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item2)).thenReturn(dto2);

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItensPorDisponibilidade(disponibilidade);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(itemCardapioRepository, times(1)).buscarItensPorDisponibilidade(disponibilidade);
        verify(itemCardapioMapper, times(2)).itemCardapioToItemCardapioBuscarDTO(any(ItemCardapio.class));
    }

    @Test
    void buscarItensPorDisponibilidadeRetornaListaVaziaQuandoNenhumDisponivel() {
        String disponibilidade = "Indisponível";
        when(itemCardapioRepository.buscarItensPorDisponibilidade(disponibilidade)).thenReturn(Collections.emptyList());

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItensPorDisponibilidade(disponibilidade);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(itemCardapioRepository, times(1)).buscarItensPorDisponibilidade(disponibilidade);
    }

    @Test
    void buscarItensPorDisponibilidadeComDisponibilidadeVaziaRetornaListaVazia() {
        String disponibilidade = "";
        when(itemCardapioRepository.buscarItensPorDisponibilidade(disponibilidade)).thenReturn(Collections.emptyList());

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarItensPorDisponibilidade(disponibilidade);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void buscarTodosItensRetornaListaDTOQuandoExistemItens() {
        int size = 10;
        int offset = 0;
        ItemCardapio item1 = criarItemCardapio(1L, "Pizza", "Pizza deliciosa", 35.0, "Disponível", "pizza.jpg");
        ItemCardapio item2 = criarItemCardapio(2L, "Pastel", "Pastel crocante", 6.0, "Disponível", "pastel.jpg");
        List<ItemCardapio> itens = Arrays.asList(item1, item2);

        ItemCardapioBuscarDTO dto1 = criarItemCardapioBuscarDTO("Pizza", "Pizza deliciosa", 35.0);
        ItemCardapioBuscarDTO dto2 = criarItemCardapioBuscarDTO("Pastel", "Pastel crocante", 6.0);

        when(itemCardapioRepository.buscarTodosItens(size, offset)).thenReturn(itens);
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item1)).thenReturn(dto1);
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item2)).thenReturn(dto2);

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarTodosItens(size, offset);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(itemCardapioRepository, times(1)).buscarTodosItens(size, offset);
        verify(itemCardapioMapper, times(2)).itemCardapioToItemCardapioBuscarDTO(any(ItemCardapio.class));
    }

    @Test
    void buscarTodosItensRetornaListaVaziaQuandoNenhumItemCadastrado() {
        int size = 10;
        int offset = 0;
        when(itemCardapioRepository.buscarTodosItens(size, offset)).thenReturn(Collections.emptyList());

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarTodosItens(size, offset);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(itemCardapioRepository, times(1)).buscarTodosItens(size, offset);
    }

    @Test
    void buscarTodosItensComPaginacaoDiferente() {
        int size = 5;
        int offset = 10;
        ItemCardapio item = criarItemCardapio(1L, "Pizza", "Pizza deliciosa", 35.0, "Disponível", "pizza.jpg");
        ItemCardapioBuscarDTO dto = criarItemCardapioBuscarDTO("Pizza", "Pizza deliciosa", 35.0);

        when(itemCardapioRepository.buscarTodosItens(size, offset)).thenReturn(Arrays.asList(item));
        when(itemCardapioMapper.itemCardapioToItemCardapioBuscarDTO(item)).thenReturn(dto);

        List<ItemCardapioBuscarDTO> resultado = itemCardapioService.buscarTodosItens(size, offset);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(itemCardapioRepository, times(1)).buscarTodosItens(size, offset);
    }

    @Test
    void cadastrarItemCardapioBemSucedidoComDTOValido() {
        ItemCardapioCriarDTO criarDTO = criarItemCardapioCriarDTO("Pizza Nova", "Pizza deliciosa", 35.0, "Disponível", "pizza.jpg");
        ItemCardapio itemCardapio = criarItemCardapio(null, "Pizza Nova", "Pizza deliciosa", 35.0, "Disponível", "pizza.jpg");

        when(itemCardapioMapper.itemCardapioCriarDTOToItemCardapio(criarDTO)).thenReturn(itemCardapio);

        assertDoesNotThrow(() -> itemCardapioService.cadastrarItemCardapio(criarDTO));

        verify(itemCardapioMapper, times(1)).itemCardapioCriarDTOToItemCardapio(criarDTO);
        verify(itemCardapioRepository, times(1)).criarNovoItemCardapio(itemCardapio);
    }

    @Test
    void cadastrarItemCardapioChamaMapeadorERepositorio() {
        ItemCardapioCriarDTO criarDTO = criarItemCardapioCriarDTO("Pastel", "Pastel crocante", 6.0, "Disponível", "pastel.jpg");
        ItemCardapio itemCardapio = criarItemCardapio(1L, "Pastel", "Pastel crocante", 6.0, "Disponível", "pastel.jpg");

        when(itemCardapioMapper.itemCardapioCriarDTOToItemCardapio(criarDTO)).thenReturn(itemCardapio);

        itemCardapioService.cadastrarItemCardapio(criarDTO);

        verify(itemCardapioMapper, times(1)).itemCardapioCriarDTOToItemCardapio(criarDTO);
        verify(itemCardapioRepository, times(1)).criarNovoItemCardapio(itemCardapio);
    }

    @Test
    void atualizarItemCardapioBemSucedidoComDTOValido() {
        ItemCardapioAtualizarDTO atualizarDTO = criarItemCardapioAtualizarDTO(1L, "Pizza Atualizada", "Pizza deliciosa", 40.0, "Disponível", "pizza.jpg");
        ItemCardapio itemCardapio = criarItemCardapio(1L, "Pizza Atualizada", "Pizza deliciosa", 40.0, "Disponível", "pizza.jpg");

        when(itemCardapioMapper.itemCardapioAtualizarDTOToItemCardapio(atualizarDTO)).thenReturn(itemCardapio);

        assertDoesNotThrow(() -> itemCardapioService.atualizarItemCardapio(atualizarDTO));

        verify(itemCardapioMapper, times(1)).itemCardapioAtualizarDTOToItemCardapio(atualizarDTO);
        verify(itemCardapioRepository, times(1)).atualizarItemCardapio(itemCardapio);
    }

    @Test
    void atualizarItemCardapioChamaMapeadorERepositorio() {
        ItemCardapioAtualizarDTO atualizarDTO = criarItemCardapioAtualizarDTO(2L, "Pastel Atualizado", "Pastel crocante", 7.0, "Disponível", "pastel.jpg");
        ItemCardapio itemCardapio = criarItemCardapio(2L, "Pastel Atualizado", "Pastel crocante", 7.0, "Disponível", "pastel.jpg");

        when(itemCardapioMapper.itemCardapioAtualizarDTOToItemCardapio(atualizarDTO)).thenReturn(itemCardapio);

        itemCardapioService.atualizarItemCardapio(atualizarDTO);

        verify(itemCardapioMapper, times(1)).itemCardapioAtualizarDTOToItemCardapio(atualizarDTO);
        verify(itemCardapioRepository, times(1)).atualizarItemCardapio(itemCardapio);
    }

    @Test
    void deletarItemCardapioBemSucedidoQuandoItemExiste() {
        Long itemId = 1L;
        ItemCardapioAtualizarDTO atualizarDTO = criarItemCardapioAtualizarDTO(itemId, "Pizza", "Pizza deliciosa", 35.0, "Disponível", "pizza.jpg");
        ItemCardapio itemCardapio = criarItemCardapio(itemId, "Pizza", "Pizza deliciosa", 35.0, "Disponível", "pizza.jpg");

        when(itemCardapioRepository.buscarItemPorId(itemId)).thenReturn(Arrays.asList(itemCardapio));

        assertDoesNotThrow(() -> itemCardapioService.deletarItemCardapio(atualizarDTO));

        verify(itemCardapioRepository, times(1)).buscarItemPorId(itemId);
        verify(itemCardapioRepository, times(1)).deletarItemCardapio(itemId);
    }

    @Test
    void deletarItemCardapioLancaRuntimeExceptionQuandoItemNaoExiste() {
        Long itemId = 999L;
        ItemCardapioAtualizarDTO atualizarDTO = criarItemCardapioAtualizarDTO(itemId, "Inexistente", "Descrição", 0.0, "Disponível", "imagem.jpg");

        when(itemCardapioRepository.buscarItemPorId(itemId)).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> itemCardapioService.deletarItemCardapio(atualizarDTO));

        verify(itemCardapioRepository, times(1)).buscarItemPorId(itemId);
        verify(itemCardapioRepository, never()).deletarItemCardapio(any());
    }

    @Test
    void deletarItemCardapioLancaRuntimeExceptionComMensagemCorreta() {
        Long itemId = 999L;
        ItemCardapioAtualizarDTO atualizarDTO = criarItemCardapioAtualizarDTO(itemId, "Inexistente", "Descrição", 0.0, "Disponível", "imagem.jpg");

        when(itemCardapioRepository.buscarItemPorId(itemId)).thenReturn(Collections.emptyList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemCardapioService.deletarItemCardapio(atualizarDTO));
        assertEquals("Item de cardápio não encontrado", exception.getMessage());
    }

    @Test
    void deletarItemCardapioComIdZeroLancaRuntimeException() {
        Long itemId = 0L;
        ItemCardapioAtualizarDTO atualizarDTO = criarItemCardapioAtualizarDTO(itemId, "Pizza", "Pizza deliciosa", 35.0, "Disponível", "pizza.jpg");

        when(itemCardapioRepository.buscarItemPorId(itemId)).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> itemCardapioService.deletarItemCardapio(atualizarDTO));
    }

    private ItemCardapio criarItemCardapio(Long id, String nome, String descricao, Double valor, String disponibilidade, String imagem) {
        ItemCardapio item = new ItemCardapio();
        item.setId(id);
        item.setNome_item(nome);
        item.setDescricao(descricao);
        item.setValor_item(valor);
        item.setDisponibilidade(disponibilidade);
        item.setImagem_prato(imagem);
        return item;
    }

    private ItemCardapioBuscarDTO criarItemCardapioBuscarDTO(String nome, String descricao, Double valor) {
        return new ItemCardapioBuscarDTO(null, nome, descricao, valor);
    }

    private ItemCardapioCriarDTO criarItemCardapioCriarDTO(String nome, String descricao, Double valor, String disponibilidade, String imagem) {
        return new ItemCardapioCriarDTO(nome, descricao, valor, disponibilidade, imagem);
    }

    private ItemCardapioAtualizarDTO criarItemCardapioAtualizarDTO(Long id, String nome, String descricao, Double valor, String disponibilidade, String imagem) {
        return new ItemCardapioAtualizarDTO(id, nome, descricao, valor, disponibilidade, imagem);
    }

}

