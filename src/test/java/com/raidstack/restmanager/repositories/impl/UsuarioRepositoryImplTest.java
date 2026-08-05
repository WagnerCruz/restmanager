package com.raidstack.restmanager.repositories.impl;

import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioSenhaDTO;
import com.raidstack.restmanager.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.raidstack.restmanager.helper.MocksHelper.mockUsuarioCliente;
import static com.raidstack.restmanager.helper.MocksHelper.mockUsuarioProprietario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioRepositoryImplTest {

    @Mock
    private JdbcClient jdbcClient;

    @Mock
    private JdbcClient.StatementSpec statementSpec;

    @Mock
    private JdbcClient.StatementSpec paramSpec;

    @Mock
    private JdbcClient.MappedQuerySpec querySpec;

    @InjectMocks
    private UsuarioRepositoryImpl repository;

    @Test
    void criarUsuarioRetornaUmQuandoSucesso() {
        Usuario usuarioMock = mockUsuarioCliente();

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.criarUsuario(usuarioMock);

        assertEquals(1, result);
    }

    @Test
    void criarUsuarioRetornaZeroQuandoFalha() {
        Usuario usuarioMock = mockUsuarioCliente();

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.criarUsuario(usuarioMock);

        assertEquals(0, result);
    }

    @Test
    void criarUsuarioComValoresNulosRetornaZeroQuandoFalha() {
        Usuario usuarioNulo = new Usuario();

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.criarUsuario(usuarioNulo);

        assertEquals(0, result);
    }

    @Test
    void criarUsuarioComProprietarioTrue() {
        Usuario usuarioProprietario = mockUsuarioProprietario();

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.criarUsuario(usuarioProprietario);

        assertEquals(1, result);
    }

    @Test
    void atualizarUsuarioRetornaUmQuandoSucesso() {
        Usuario usuarioMock = mockUsuarioCliente();
        usuarioMock.setLogin("joao_atualizado");
        usuarioMock.setEmail("joao_atualizado@gmail.com");

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.atualizarUsuario(mockUsuarioCliente());

        assertEquals(1, result);
    }

    @Test
    void atualizarUsuarioRetornaZeroQuandoFalha() {
        Usuario usuarioMock = mockUsuarioCliente();
        usuarioMock.setLogin("joao_atualizado");
        usuarioMock.setEmail("joao_atualizado@gmail.com");

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.atualizarUsuario(usuarioMock);

        assertEquals(0, result);
    }

    @Test
    void atualizarUsuarioProprietarioSucesso() {
        Usuario usuarioEdit = mockUsuarioProprietario();

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.atualizarUsuario(usuarioEdit);

        assertEquals(1, result);
    }

    @Test
    void atualizarSenhaUsuarioRetornaUmQuandoSucesso() {
        UsuarioSenhaDTO dto = new UsuarioSenhaDTO(1, null, null, "novaSenha");

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.atualizarSenhaUsuario(dto);

        assertEquals(1, result);
    }

    @Test
    void atualizarSenhaUsuarioRetornaZeroQuandoFalha() {
        UsuarioSenhaDTO dto = new UsuarioSenhaDTO(999, null, null, "novaSenha");

        this.initStatementSpec();
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.atualizarSenhaUsuario(dto);

        assertEquals(0, result);
    }

    @Test
    void deletarUsuarioRetornaUmQuandoSucesso() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(1);

        Integer result = repository.deletarUsuario(1L);

        assertEquals(1, result);
    }

    @Test
    void deletarUsuarioRetornaZeroQuandoFalha() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 999L)).thenReturn(paramSpec);
        when(paramSpec.update()).thenReturn(0);

        Integer result = repository.deletarUsuario(999L);

        assertEquals(0, result);
    }

    @Test
    void buscarUsuarioPorCPFRetornaOptionalQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("cpf", "12345678900")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.of(mockUsuarioCliente()));

        Optional<Usuario> result = repository.buscarUsuarioPorCPF("12345678900");

        assertTrue(result.isPresent());
        assertEquals("João Silva", result.get().getNome());
    }

    @Test
    void buscarUsuarioPorCPFRetornaEmptyQuandoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("cpf", "99999999999")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.empty());

        Optional<Usuario> result = repository.buscarUsuarioPorCPF("99999999999");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorIdRetornaOptionalQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 1L)).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.of(mockUsuarioCliente()));

        Optional<Usuario> result = repository.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void buscarPorIdRetornaEmptyQuandoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("id", 999L)).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.empty());

        Optional<Usuario> result = repository.buscarPorId(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorLoginRetornaOptionalQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("login", "joao")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.of(mockUsuarioCliente()));

        Optional<Usuario> result = repository.buscarPorLogin("joao");

        assertTrue(result.isPresent());
        assertEquals("joao", result.get().getLogin());
    }

    @Test
    void buscarPorLoginRetornaEmptyQuandoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("login", "inexistente")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.empty());

        Optional<Usuario> result = repository.buscarPorLogin("inexistente");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorCPFRetornaOptionalQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("cpf", "12345678900")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.of(mockUsuarioCliente()));

        Optional<Usuario> result = repository.buscarPorCPF("12345678900");

        assertTrue(result.isPresent());
        assertEquals("12345678900", result.get().getCpf());
    }

    @Test
    void buscarPorCPFRetornaEmptyQuandoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("cpf", "00000000000")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.empty());

        Optional<Usuario> result = repository.buscarPorCPF("00000000000");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorEmailRetornaOptionalQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("email", "joao@email.com")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.of(mockUsuarioCliente()));

        Optional<Usuario> result = repository.buscarPorEmail("joao@email.com");

        assertTrue(result.isPresent());
        assertEquals("joao@email.com", result.get().getEmail());
    }

    @Test
    void buscarPorEmailRetornaEmptyQuandoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("email", "inexistente@email.com")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.empty());

        Optional<Usuario> result = repository.buscarPorEmail("inexistente@email.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorNomeRetornaListaQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "%João%")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockUsuarioCliente()));

        List<Usuario> result = repository.buscarPorNome("João");

        assertEquals(1, result.size());
        assertEquals("João Silva", result.get(0).getNome());
    }

    @Test
    void buscarPorNomeRetornaVazioQuandoNaoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "%Inexistente%")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<Usuario> result = repository.buscarPorNome("Inexistente");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorNomeComStringVazia() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("nome", "%%")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<Usuario> result = repository.buscarPorNome("");

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarTodosRetornaListaPaginada() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 10)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 0)).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockUsuarioCliente()));

        List<Usuario> result = repository.buscarTodos(10, 0);

        assertEquals(1, result.size());
    }

    @Test
    void buscarTodosComOffsetPositivoSubtraiUm() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 10)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 4)).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockUsuarioCliente()));

        List<Usuario> result = repository.buscarTodos(10, 5);

        assertEquals(1, result.size());
    }

    @Test
    void buscarTodosRetornaVazioQuandoOffsetForaDoAlcance() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("size", 10)).thenReturn(paramSpec);
        when(paramSpec.param("offset", 1000)).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<Usuario> result = repository.buscarTodos(10, 1001);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarUsuarioPorCpfEmailLoginRetornaListaQuandoExiste() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("cpf", "12345678900")).thenReturn(paramSpec);
        when(paramSpec.param("email", "joao@email.com")).thenReturn(paramSpec);
        when(paramSpec.param("login", "joao")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(Arrays.asList(mockUsuarioCliente()));

        List<Usuario> result = repository.buscarUsuarioPorCpfEmailLogin(mockUsuarioCliente());

        assertEquals(1, result.size());
    }

    @Test
    void buscarUsuarioPorCpfEmailLoginRetornaVazioQuandoNaoExiste() {
        Usuario usuarioNaoExistente = new Usuario();
        usuarioNaoExistente.setCpf("00000000000");
        usuarioNaoExistente.setEmail("nao@existe.com");
        usuarioNaoExistente.setLogin("naoexiste");

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("cpf", "00000000000")).thenReturn(paramSpec);
        when(paramSpec.param("email", "nao@existe.com")).thenReturn(paramSpec);
        when(paramSpec.param("login", "naoexiste")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(new ArrayList<>());

        List<Usuario> result = repository.buscarUsuarioPorCpfEmailLogin(usuarioNaoExistente);

        assertTrue(result.isEmpty());
    }

    @Test
    void validaUsuarioPorLoginESenhaRetornaOptionalQuandoValido() {
        UsuarioSenhaDTO dto = new UsuarioSenhaDTO(1, null, "joao", "senha123");

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("login", "joao")).thenReturn(paramSpec);
        when(paramSpec.param("senha", "senha123")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.of(mockUsuarioCliente()));

        Optional<Usuario> result = repository.validaUsuarioPorLoginESenha(dto);

        assertTrue(result.isPresent());
        assertEquals(dto.id(), result.get().getId().intValue());
        assertEquals(dto.login(), result.get().getLogin());
        assertEquals(dto.senha(), result.get().getSenha());
    }

    @Test
    void validaUsuarioPorLoginESenhaRetornaEmptyQuandoInvalido() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param("login", "invalido")).thenReturn(paramSpec);
        when(paramSpec.param("senha", "senhaerrada")).thenReturn(paramSpec);
        when(paramSpec.query(Usuario.class)).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.empty());

        UsuarioSenhaDTO invalidDto = new UsuarioSenhaDTO(1, "00000000000", "invalido", "senhaerrada");
        Optional<Usuario> result = repository.validaUsuarioPorLoginESenha(invalidDto);

        assertTrue(result.isEmpty());
    }

    public void initStatementSpec() {
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(paramSpec);
        when(paramSpec.param(anyString(), any())).thenReturn(paramSpec);
    }
}

