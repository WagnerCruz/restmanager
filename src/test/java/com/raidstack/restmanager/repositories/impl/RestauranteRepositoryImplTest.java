package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.entity.Restaurante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.raidstack.restmanager.helper.MocksHelper.mockRestaurante;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteRepositoryImplTest {

    @Mock
    private JdbcClient jdbcClient;

    @Mock
    private JdbcClient.StatementSpec statementSpec;

    @Mock
    private JdbcClient.StatementSpec paramSpec;

    @Mock
    private JdbcClient.MappedQuerySpec querySpec;

    @InjectMocks
    private RestauranteRepositoryImpl repository;

    @Test
    void buscarRestaurantePorNomeRetornaListaQuandoExisteNome() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Bom Sabor")).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockRestaurante()));

        List<Restaurante> result = repository.buscarRestaurantePorNome("Bom Sabor");

        assertEquals(1, result.size());
        assertEquals("Bom Sabor", result.get(0).getNome());
    }

    @Test
    void buscarRestaurantePorNomeRetornaVazioQuandoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Inexistente")).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<Restaurante> result = repository.buscarRestaurantePorNome("Inexistente");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarTodosRestaurantesRetornaListaPaginada() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 10)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 0)).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockRestaurante()));

        List<Restaurante> result = repository.buscarTodosRestaurantes(10, 0);

        assertEquals(1, result.size());
    }

    @Test
    void buscarTodosRestaurantesComOffsetGrandeRetornaVazio() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 10)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 1000)).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<Restaurante> result = repository.buscarTodosRestaurantes(10, 1000);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarRestaurantePorTipoCozinhaRetornaListaQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("tipoCozinha", "Italiana")).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockRestaurante()));

        List<Restaurante> result = repository.buscarRestaurantePorTipoCozinha("Italiana");

        assertEquals(1, result.size());
        assertEquals("Italiana", result.get(0).getTipo_cozinha());
    }

    @Test
    void buscarRestaurantePorTipoCozinhaComStringVaziaRetornaVazio() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("tipoCozinha", "")).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<Restaurante> result = repository.buscarRestaurantePorTipoCozinha("");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorHorarioRetornaListaQuandoDentroDoHorario() {
        Time hora = Time.valueOf("12:00:00");
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("horaInicio", hora)).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockRestaurante()));

        List<Restaurante> result = repository.buscarPorHorario(hora);

        assertEquals(1, result.size());
    }

    @Test
    void buscarPorHorarioForaDoHorarioRetornaVazio() {
        Time hora = Time.valueOf("23:00:00");
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("horaInicio", hora)).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<Restaurante> result = repository.buscarPorHorario(hora);

        assertTrue(result.isEmpty());
    }

    @Test
    void cadastrarRestauranteRetornaUmQuandoSucesso() {
        Restaurante novo = new Restaurante();
        novo.setNome("Novo");
        novo.setTipo_cozinha("Japonesa");
        novo.setHorario_inicio_functo(Time.valueOf("11:00:00"));
        novo.setHorario_fim_functo(Time.valueOf("23:00:00"));
        novo.setId_usuario(2);

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Novo")).thenReturn(paramSpec);
        when(paramSpec.param("tipoCozinha", "Japonesa")).thenReturn(paramSpec);
        when(paramSpec.param("horaInicio", Time.valueOf("11:00:00"))).thenReturn(paramSpec);
        when(paramSpec.param("horaFim", Time.valueOf("23:00:00"))).thenReturn(paramSpec);
        when(paramSpec.param("id_usuario", 2)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.cadastrarRestaurante(novo);

        assertEquals(1, result);
    }

    @Test
    void cadastrarRestauranteRetornaZeroQuandoFalha() {
        Restaurante novo = new Restaurante();
        novo.setNome("Novo");

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "Novo")).thenReturn(paramSpec);
        when(paramSpec.param("tipoCozinha", null)).thenReturn(paramSpec);
        when(paramSpec.param("horaInicio", null)).thenReturn(paramSpec);
        when(paramSpec.param("horaFim", null)).thenReturn(paramSpec);
        when(paramSpec.param("id_usuario", null)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.cadastrarRestaurante(novo);

        assertEquals(0, result);
    }

    @Test
    void atualizarRestauranteRetornaUmQuandoSucesso() {
        Restaurante edit = new Restaurante();
        edit.setId(1L);
        edit.setNome("Editado");
        edit.setTipo_cozinha("Mexicana");
        edit.setHorario_inicio_functo(Time.valueOf("09:00:00"));
        edit.setHorario_fim_functo(Time.valueOf("21:00:00"));
        edit.setId_usuario(3);

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.param("nome", "Editado")).thenReturn(paramSpec);
        when(paramSpec.param("tipoCozinha", "Mexicana")).thenReturn(paramSpec);
        when(paramSpec.param("horaInicio", Time.valueOf("09:00:00"))).thenReturn(paramSpec);
        when(paramSpec.param("horaFim", Time.valueOf("21:00:00"))).thenReturn(paramSpec);
        when(paramSpec.param("idDono", 3)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.atualizarRestaurante(edit);

        assertEquals(1, result);
    }

    @Test
    void atualizarRestauranteRetornaZeroQuandoFalha() {
        Restaurante edit = new Restaurante();
        edit.setId(1L);

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.param("nome", null)).thenReturn(paramSpec);
        when(paramSpec.param("tipoCozinha", null)).thenReturn(paramSpec);
        when(paramSpec.param("horaInicio", null)).thenReturn(paramSpec);
        when(paramSpec.param("horaFim", null)).thenReturn(paramSpec);
        when(paramSpec.param("idDono", null)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.atualizarRestaurante(edit);

        assertEquals(0, result);
    }

    @Test
    void deletarRestauranteRetornaUmQuandoSucesso() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.deletarRestaurante(1L);

        assertEquals(1, result);
    }

    @Test
    void deletarRestauranteRetornaZeroQuandoFalha() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 999L)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.deletarRestaurante(999L);

        assertEquals(0, result);
    }

    @Test
    void buscarPorIdRetornaOptionalQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(java.util.Optional.of(mockRestaurante()));

        java.util.Optional<Restaurante> result = repository.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void buscarPorIdRetornaEmptyQuandoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 999L)).thenReturn(paramSpec);
        when(paramSpec.query(Restaurante.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(java.util.Optional.empty());

        java.util.Optional<Restaurante> result = repository.buscarPorId(999L);

        assertTrue(result.isEmpty());
    }
}

