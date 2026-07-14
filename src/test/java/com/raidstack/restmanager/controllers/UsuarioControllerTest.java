package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioBuscarDTO;
import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioCriarDTO;
import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioSenhaDTO;
import com.raidstack.restmanager.services.UsuarioService;
import com.raidstack.restmanager.vo.UsuarioVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private UsuarioController controller;

    @Mock
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        controller = new UsuarioController(service);
    }

    @Test
    void getUsuariosRetornaOkComLista() {
        UsuarioVO vo1 = criarUsuarioVO(1, "João", "123", "joao@email.com", "joao");
        UsuarioVO vo2 = criarUsuarioVO(2, "Maria", "456", "maria@email.com", "maria");
        when(service.findAll()).thenReturn(Arrays.asList(vo1, vo2));

        ResponseEntity<List<UsuarioVO>> response = controller.getUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).findAll();
    }

    @Test
    void getUsuariosRetornaVazioQuandoNenhumUsuario() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UsuarioVO>> response = controller.getUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getValidaUsuarioRetornaNoContentQuandoValido() {
        UsuarioSenhaDTO dto = criarUsuarioSenhaDTO(1, "123", "login", "senha");

        ResponseEntity<Void> response = controller.getValidaUsuario(dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).validarLoginUsuario(dto);
    }

    @Test
    void getValidaUsuarioPropagaExcecaoQuandoServicoLanca() {
        UsuarioSenhaDTO dto = criarUsuarioSenhaDTO(1, "123", "login", "senhaErrada");
        doThrow(new RuntimeException("invalid")).when(service).validarLoginUsuario(dto);

        assertThrows(RuntimeException.class, () -> controller.getValidaUsuario(dto));
        verify(service, times(1)).validarLoginUsuario(dto);
    }

    @Test
    void getUsuarioByNomeRetornaOkComLista() {
        UsuarioBuscarDTO buscarDTO = new UsuarioBuscarDTO(null, "João", null, null);
        UsuarioVO vo = criarUsuarioVO(1, "João", "123", "joao@email.com", "joao");
        when(service.findByNome("João")).thenReturn(Arrays.asList(vo));

        ResponseEntity<List<UsuarioVO>> response = controller.getUsuarioByNome(buscarDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).findByNome("João");
    }

    @Test
    void criarUsuarioRetornaCreated() {
        UsuarioCriarDTO criarDTO = criarUsuarioCriarDTO("João", "123", "joao@email.com", "joao", "senha");

        ResponseEntity<Void> response = controller.criarUsuario(criarDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service, times(1)).criarUsuario(criarDTO);
    }

    @Test
    void atualizarUsuarioRetornaOk() {
        UsuarioAtualizarDTO atualizarDTO = new UsuarioAtualizarDTO(1L, "João", "123", "joao@email.com", "joao", "Rua", 1, "N");

        ResponseEntity<Void> response = controller.atualizarUsuario(atualizarDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).atualizarUsuario(atualizarDTO);
    }

    @Test
    void atualizarSenhaUsuarioRetornaOk() {
        UsuarioSenhaDTO dto = criarUsuarioSenhaDTO(1, "123", "joao", "novaSenha");

        ResponseEntity<Void> response = controller.atualizarSenhaUsuario(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).atualizarSenhaUsuario(dto);
    }

    @Test
    void deletarUsuarioRetornaOk() {
        UsuarioAtualizarDTO atualizarDTO = new UsuarioAtualizarDTO(1L, "João", "123", "joao@email.com", "joao", "Rua", 1, "N");

        ResponseEntity<Void> response = controller.deletarUsuario(atualizarDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).deletarUsuario(atualizarDTO);
    }

    private UsuarioVO criarUsuarioVO(int id, String nome, String cpf, String email, String login) {
        return new UsuarioVO(id, nome, cpf, email, login, "Rua", 1, "N", LocalDateTime.now());
    }

    private UsuarioSenhaDTO criarUsuarioSenhaDTO(int id, String cpf, String login, String senha) {
        return new UsuarioSenhaDTO(id, cpf, login, senha);
    }

    private UsuarioCriarDTO criarUsuarioCriarDTO(String nome, String cpf, String email, String login, String senha) {
        return new UsuarioCriarDTO(nome, cpf, email, login, senha, "Rua", 1, "N");
    }

}

