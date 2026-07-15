package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioBuscarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioCriarDTO;
import com.raidstack.restmanager.services.TipoUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioControllerTest {

    @Mock
    private TipoUsuarioService tipoUsuarioService;

    @InjectMocks
    private TipoUsuarioController controller;

    @BeforeEach
    void setup() {
    }

    @Test
    void pesquisarTodosTiposUsuario_deveRetornarOk() {
        TipoUsuarioBuscarDTO dto = mock(TipoUsuarioBuscarDTO.class);
        when(tipoUsuarioService.buscarTodosTipoUsuario(10, 1)).thenReturn(List.of(dto));

        ResponseEntity<List<TipoUsuarioBuscarDTO>> resp = controller.pesquisarTodosTiposUsuario();

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertSame(dto, resp.getBody().get(0));
        verify(tipoUsuarioService).buscarTodosTipoUsuario(10, 1);
    }

    @Test
    void pesquisarTodosTiposUsuario_devePropagarExcecao() {
        when(tipoUsuarioService.buscarTodosTipoUsuario(10, 1)).thenThrow(new RuntimeException("erro"));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.pesquisarTodosTiposUsuario());
        assertEquals("erro", ex.getMessage());
    }

    @Test
    void pesquisarTiposUsuarioPorNome_deveRetornarOkEListaVazia() {
        TipoUsuarioBuscarDTO dto = mock(TipoUsuarioBuscarDTO.class);
        when(tipoUsuarioService.buscarTipoUsuarioPorNome("x")).thenReturn(List.of(dto));

        ResponseEntity<List<TipoUsuarioBuscarDTO>> resp = controller.pesquisarTiposUsuarioPorNome("x");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());

        when(tipoUsuarioService.buscarTipoUsuarioPorNome("none")).thenReturn(Collections.emptyList());
        ResponseEntity<List<TipoUsuarioBuscarDTO>> resp2 = controller.pesquisarTiposUsuarioPorNome("none");
        assertTrue(resp2.getBody().isEmpty());
    }

    @Test
    void cadastrarTipoUsuario_deveRetornarCreated() {
        TipoUsuarioCriarDTO criar = mock(TipoUsuarioCriarDTO.class);
        when(criar.nome_tipo()).thenReturn("nome");
        doNothing().when(tipoUsuarioService).cadastrarTipoUsuario(criar);

        ResponseEntity<Void> resp = controller.cadastrarTipoUsuario(criar);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        verify(tipoUsuarioService).cadastrarTipoUsuario(criar);
    }

    @Test
    void cadastrarTipoUsuario_devePropagarExcecao() {
        TipoUsuarioCriarDTO criar = mock(TipoUsuarioCriarDTO.class);
        when(criar.nome_tipo()).thenReturn("n");
        doThrow(new RuntimeException("fail")).when(tipoUsuarioService).cadastrarTipoUsuario(criar);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.cadastrarTipoUsuario(criar));
        assertEquals("fail", ex.getMessage());
    }

    @Test
    void atualizarTipoUsuario_deveRetornarOk() {
        TipoUsuarioAtualizarDTO atualizar = mock(TipoUsuarioAtualizarDTO.class);
        when(atualizar.nome_tipo()).thenReturn("n");
        doNothing().when(tipoUsuarioService).atualizarTipoUsuario(atualizar);

        ResponseEntity<Void> resp = controller.atualizarTipoUsuario(atualizar);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        verify(tipoUsuarioService).atualizarTipoUsuario(atualizar);
    }

    @Test
    void atualizarTipoUsuario_devePropagarExcecao() {
        TipoUsuarioAtualizarDTO atualizar = mock(TipoUsuarioAtualizarDTO.class);
        when(atualizar.nome_tipo()).thenReturn("n");
        doThrow(new RuntimeException("err")).when(tipoUsuarioService).atualizarTipoUsuario(atualizar);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.atualizarTipoUsuario(atualizar));
        assertEquals("err", ex.getMessage());
    }

    @Test
    void deletarTipoUsuario_deveRetornarOk() {
        TipoUsuarioAtualizarDTO atualizar = mock(TipoUsuarioAtualizarDTO.class);
        when(atualizar.nome_tipo()).thenReturn("n");
        doNothing().when(tipoUsuarioService).deletarTipoUsuario(atualizar);

        ResponseEntity<Void> resp = controller.deletarTipoUsuario(atualizar);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        verify(tipoUsuarioService).deletarTipoUsuario(atualizar);
    }

    @Test
    void deletarTipoUsuario_devePropagarExcecao() {
        TipoUsuarioAtualizarDTO atualizar = mock(TipoUsuarioAtualizarDTO.class);
        when(atualizar.nome_tipo()).thenReturn("n");
        doThrow(new RuntimeException("boom")).when(tipoUsuarioService).deletarTipoUsuario(atualizar);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.deletarTipoUsuario(atualizar));
        assertEquals("boom", ex.getMessage());
    }

}
