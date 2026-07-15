package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.entity.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioRepositoryImplTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private JdbcClient jdbcClient;

    private TipoUsuarioRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = new TipoUsuarioRepositoryImpl(jdbcClient);
    }

    @Test
    void buscarTipoUsuarioPorId_deveRetornarLista() {
        TipoUsuario t = mock(TipoUsuario.class);
        when(jdbcClient.sql("SELECT * FROM tipo_usuario WHERE id = :id").param("id", 1L).query(TipoUsuario.class).list())
                .thenReturn(List.of(t));

        List<TipoUsuario> resultado = repository.buscarTipoUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertSame(t, resultado.get(0));
        verify(jdbcClient).sql("SELECT * FROM tipo_usuario WHERE id = :id");
    }

    @Test
    void buscarTipoUsuarioPorId_devePropagarExcecao() {
        when(jdbcClient.sql("SELECT * FROM tipo_usuario WHERE id = :id").param("id", 2L).query(TipoUsuario.class).list())
                .thenThrow(new RuntimeException("boom"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> repository.buscarTipoUsuarioPorId(2L));
        assertEquals("boom", ex.getMessage());
    }

    @Test
    void buscarTodosTipoUsuario_offsetZero() {
        TipoUsuario t = mock(TipoUsuario.class);
        when(jdbcClient.sql("SELECT * FROM tipo_usuario LIMIT :size OFFSET :offset").param("size", 5).param("TipoUsuario", 0).query(TipoUsuario.class).list())
                .thenReturn(List.of(t));

        List<TipoUsuario> resultado = repository.buscarTodosTipoUsuario(5, 0);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarTodosTipoUsuario_offsetMaiorQueZero() {
        TipoUsuario t = mock(TipoUsuario.class);
        when(jdbcClient.sql("SELECT * FROM tipo_usuario LIMIT :size OFFSET :offset").param("size", 3).param("TipoUsuario", 1).query(TipoUsuario.class).list())
                .thenReturn(List.of(t));

        List<TipoUsuario> resultado = repository.buscarTodosTipoUsuario(3, 2);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarTipoUsuarioPorNome_deveRetornarListaEVazio() {
        TipoUsuario t = mock(TipoUsuario.class);
        when(jdbcClient.sql("SELECT * FROM tipo_usuario WHERE nome_tipo = :nome").param("nome", "admin").query(TipoUsuario.class).list())
                .thenReturn(List.of(t));

        List<TipoUsuario> resultado = repository.buscarTipoUsuarioPorNome("admin");
        assertEquals(1, resultado.size());

        when(jdbcClient.sql("SELECT * FROM tipo_usuario WHERE nome_tipo = :nome").param("nome", "none").query(TipoUsuario.class).list())
                .thenReturn(Collections.emptyList());

        List<TipoUsuario> vazio = repository.buscarTipoUsuarioPorNome("none");
        assertTrue(vazio.isEmpty());
    }

    @Test
    void criarNovoTipoUsuario_deveRetornarInt() {
        TipoUsuario t = mock(TipoUsuario.class);
        when(t.getNome_tipo()).thenReturn("nome");
        when(jdbcClient.sql("INSERT INTO tipo_usuario (nome_tipo) VALUES (:nome)").param("nome", "nome").update()).thenReturn(1);

        Integer r = repository.criarNovoTipoUsuario(t);
        assertEquals(1, r);
    }

    @Test
    void criarNovoTipoUsuario_devePropagarExcecao() {
        TipoUsuario t = mock(TipoUsuario.class);
        when(t.getNome_tipo()).thenReturn("x");
        when(jdbcClient.sql("INSERT INTO tipo_usuario (nome_tipo) VALUES (:nome)").param("nome", "x").update())
                .thenThrow(new RuntimeException("fail"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> repository.criarNovoTipoUsuario(t));
        assertEquals("fail", ex.getMessage());
    }

    @Test
    void atualizarTipoUsuario_deveRetornarInt() {
        TipoUsuario t = mock(TipoUsuario.class);
        when(t.getId()).thenReturn(7L);
        when(t.getNome_tipo()).thenReturn("n");
        when(jdbcClient.sql("UPDATE item_usuario SET nome_tipo= :nome WHERE id = :id").param("id", 7L).param("nome", "n").update()).thenReturn(1);

        Integer r = repository.atualizarTipoUsuario(t);
        assertEquals(1, r);
    }

    @Test
    void deletarTipoUsuario_deveRetornarInt() {
        when(jdbcClient.sql("DELETE FROM item_usuario WHERE id = :id").param("id", 9L).update()).thenReturn(1);

        Integer r = repository.deletarTipoUsuario(9L);
        assertEquals(1, r);
    }

}
