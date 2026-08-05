package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioCriarDTO;
import com.raidstack.restmanager.dtos.UsuarioDTO.UsuarioSenhaDTO;
import com.raidstack.restmanager.entity.Usuario;
import com.raidstack.restmanager.mapper.UsuarioMapper;
import com.raidstack.restmanager.repositories.UsuarioRepository;
import com.raidstack.restmanager.services.exceptions.ResourceBadRequestException;
import com.raidstack.restmanager.services.exceptions.ResourceConflictException;
import com.raidstack.restmanager.services.exceptions.ResourceExceptionDefault;
import com.raidstack.restmanager.services.exceptions.ResourceNotFoundException;
import com.raidstack.restmanager.vo.UsuarioVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.raidstack.restmanager.helper.MocksHelper.mockUsuarioAtualizarDTO;
import static com.raidstack.restmanager.helper.MocksHelper.mockUsuarioCliente;
import static com.raidstack.restmanager.helper.MocksHelper.mockUsuarioCriarDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(usuarioRepository, usuarioMapper);
    }

    @Test
    void findByIdRetornaUsuarioDTOQuandoIdExiste() {
        
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setNome("João Silva");

        UsuarioAtualizarDTO usuarioDTOEsperado = new UsuarioAtualizarDTO(
                userId, "João Silva", "12345678901", "joao@email.com", "joao_silva", "Rua A", 123, "N"
        );

        when(usuarioRepository.buscarPorId(userId)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.usuarioToUsuarioDTO(usuario)).thenReturn(usuarioDTOEsperado);
        
        UsuarioAtualizarDTO resultado = usuarioService.findById(userId);
        
        assertNotNull(resultado);
        assertEquals(usuarioDTOEsperado, resultado);
        verify(usuarioRepository, times(1)).buscarPorId(userId);
        verify(usuarioMapper, times(1)).usuarioToUsuarioDTO(usuario);
    }

    @Test
    void findByIdLancaResourceNotFoundExceptionQuandoIdNaoExiste() {
        
        Long userId = 999L;
        when(usuarioRepository.buscarPorId(userId)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findById(userId));
        verify(usuarioRepository, times(1)).buscarPorId(userId);
        verify(usuarioMapper, never()).usuarioToUsuarioDTO(any());
    }

    @Test
    void findByIdComIdZeroLancaResourceNotFoundException() {
        
        Long userId = 0L;
        when(usuarioRepository.buscarPorId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findById(userId));
        verify(usuarioRepository, times(1)).buscarPorId(userId);
    }

    @Test
    void findByLoginRetornaUsuarioVOQuandoLoginExiste() {
        
        String login = "joao_silva";
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin(login);

        UsuarioVO usuarioVOEsperado = new UsuarioVO(1, "João Silva", "12345678901", "joao@email.com",
                login, "Rua A", 123, "N", LocalDateTime.now());

        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.usuarioToUsuarioVO(usuario)).thenReturn(usuarioVOEsperado);

        UsuarioVO resultado = usuarioService.findByLogin(login);

        assertNotNull(resultado);
        assertEquals(usuarioVOEsperado, resultado);
        verify(usuarioRepository, times(1)).buscarPorLogin(login);
        verify(usuarioMapper, times(1)).usuarioToUsuarioVO(usuario);
    }

    @Test
    void findByLoginLancaResourceNotFoundExceptionQuandoLoginNaoExiste() {
        String login = "nao_existe";
        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findByLogin(login));
        verify(usuarioRepository, times(1)).buscarPorLogin(login);
        verify(usuarioMapper, never()).usuarioToUsuarioVO(any());
    }

    @Test
    void findByLoginComLoginVazioLancaResourceNotFoundException() {
        String login = "";
        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findByLogin(login));
    }

    @Test
    void findByNomeRetornaListaDeUsuariosVOQuandoNomeExiste() {
        
        String nome = "João";
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("João Silva");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("João Santos");

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        UsuarioVO usuarioVO1 = new UsuarioVO(1, "João Silva", "12345678901", "joao1@email.com",
                "joao_silva", "Rua A", 123, "N", LocalDateTime.now());
        UsuarioVO usuarioVO2 = new UsuarioVO(2, "João Santos", "12345678902", "joao2@email.com",
                "joao_santos", "Rua B", 456, "S", LocalDateTime.now());

        when(usuarioRepository.buscarPorNome("joão")).thenReturn(usuarios);
        when(usuarioMapper.usuarioToUsuarioVO(usuario1)).thenReturn(usuarioVO1);
        when(usuarioMapper.usuarioToUsuarioVO(usuario2)).thenReturn(usuarioVO2);

        List<UsuarioVO> resultado = usuarioService.findByNome(nome);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(usuarioVO1, resultado.get(0));
        assertEquals(usuarioVO2, resultado.get(1));
        verify(usuarioRepository, times(1)).buscarPorNome("joão");
        verify(usuarioMapper, times(2)).usuarioToUsuarioVO(any());
    }

    @Test
    void findByNomeLancaResourceNotFoundExceptionQuandoNenhumUsuarioEncontrado() {
        String nome = "NaoExiste";
        when(usuarioRepository.buscarPorNome("naoexiste")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findByNome(nome));
        verify(usuarioRepository, times(1)).buscarPorNome("naoexiste");
        verify(usuarioMapper, never()).usuarioToUsuarioVO(any());
    }

    @Test
    void findByNomeComNomeVazioLancaResourceNotFoundException() {
        String nome = "";
        when(usuarioRepository.buscarPorNome("")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findByNome(nome));
    }

    @Test
    void findAllRetornaListaDeUsuariosVO() {
        
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("João");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("Maria");

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        UsuarioVO usuarioVO1 = new UsuarioVO(1, "João", "12345678901", "joao@email.com",
                "joao", "Rua A", 123, "N", LocalDateTime.now());
        UsuarioVO usuarioVO2 = new UsuarioVO(2, "Maria", "12345678902", "maria@email.com",
                "maria", "Rua B", 456, "N", LocalDateTime.now());

        when(usuarioRepository.buscarTodos(10, 1)).thenReturn(usuarios);
        when(usuarioMapper.usuarioToUsuarioVO(usuario1)).thenReturn(usuarioVO1);
        when(usuarioMapper.usuarioToUsuarioVO(usuario2)).thenReturn(usuarioVO2);

        List<UsuarioVO> resultado = usuarioService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).buscarTodos(10, 1);
        verify(usuarioMapper, times(2)).usuarioToUsuarioVO(any());
    }

    @Test
    void findAllRetornaListaVaziaQuandoNenhumUsuarioCadastrado() {
        when(usuarioRepository.buscarTodos(10, 1)).thenReturn(Collections.emptyList());

        List<UsuarioVO> resultado = usuarioService.findAll();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(usuarioRepository, times(1)).buscarTodos(10, 1);
    }

    @Test
    void criarUsuarioBemSucedidoQuandoCredenciaisValidas() {
        UsuarioCriarDTO usuarioCriarDTO = mockUsuarioCriarDTO();

        Usuario usuario = mockUsuarioCliente();

        when(usuarioMapper.usuarioCriarDTOToUsuario(usuarioCriarDTO)).thenReturn(usuario);
        when(usuarioRepository.buscarUsuarioPorCPF(usuarioCriarDTO.cpf())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorLogin(usuarioCriarDTO.login())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorEmail(usuarioCriarDTO.email())).thenReturn(Optional.empty());
        when(usuarioRepository.criarUsuario(usuario)).thenReturn(1);

        assertDoesNotThrow(() -> usuarioService.criarUsuario(usuarioCriarDTO));

        verify(usuarioMapper, times(1)).usuarioCriarDTOToUsuario(usuarioCriarDTO);
        verify(usuarioRepository, times(1)).buscarUsuarioPorCPF(usuarioCriarDTO.cpf());
        verify(usuarioRepository, times(1)).buscarPorLogin(usuarioCriarDTO.login());
        verify(usuarioRepository, times(1)).buscarPorEmail(usuarioCriarDTO.email());
        verify(usuarioRepository, times(1)).criarUsuario(usuario);
    }

    @Test
    void criarUsuarioLancaResourceExceptionDefaultQuandoInsercaoFalha() {
        UsuarioCriarDTO usuarioCriarDTO = mockUsuarioCriarDTO();

        Usuario usuario = mockUsuarioCliente();

        when(usuarioMapper.usuarioCriarDTOToUsuario(usuarioCriarDTO)).thenReturn(usuario);
        when(usuarioRepository.buscarUsuarioPorCPF(usuarioCriarDTO.cpf())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorLogin(usuarioCriarDTO.login())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorEmail(usuarioCriarDTO.email())).thenReturn(Optional.empty());
        when(usuarioRepository.criarUsuario(usuario)).thenReturn(0);

        assertThrows(ResourceExceptionDefault.class, () -> usuarioService.criarUsuario(usuarioCriarDTO));
        verify(usuarioRepository, times(1)).criarUsuario(usuario);
    }

    @Test
    void criarUsuarioLancaResourceConflictExceptionQuandoCPFJaCadastrado() {
        UsuarioCriarDTO usuarioCriarDTO = mockUsuarioCriarDTO();

        Usuario usuario = mockUsuarioCliente();
        usuario.setId(null);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(2L);

        when(usuarioMapper.usuarioCriarDTOToUsuario(usuarioCriarDTO)).thenReturn(usuario);
        when(usuarioRepository.buscarUsuarioPorCPF(usuarioCriarDTO.cpf())).thenReturn(Optional.of(usuarioExistente));

        assertThrows(ResourceConflictException.class, () -> usuarioService.criarUsuario(usuarioCriarDTO));
        verify(usuarioRepository, never()).criarUsuario(any());
    }

    @Test
    void criarUsuarioLancaResourceConflictExceptionQuandoLoginJaCadastrado() {
        UsuarioCriarDTO usuarioCriarDTO = mockUsuarioCriarDTO();

        Usuario usuario = mockUsuarioCliente();
        usuario.setId(null);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(2L);

        when(usuarioMapper.usuarioCriarDTOToUsuario(usuarioCriarDTO)).thenReturn(usuario);
        when(usuarioRepository.buscarUsuarioPorCPF(usuarioCriarDTO.cpf())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorLogin(usuarioCriarDTO.login())).thenReturn(Optional.of(usuarioExistente));

        assertThrows(ResourceConflictException.class, () -> usuarioService.criarUsuario(usuarioCriarDTO));
        verify(usuarioRepository, never()).criarUsuario(any());
    }

    @Test
    void criarUsuarioLancaResourceConflictExceptionQuandoEmailJaCadastrado() {
        UsuarioCriarDTO usuarioCriarDTO = mockUsuarioCriarDTO();

        Usuario usuario = mockUsuarioCliente();
        usuario.setId(null);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(2L);

        when(usuarioMapper.usuarioCriarDTOToUsuario(usuarioCriarDTO)).thenReturn(usuario);
        when(usuarioRepository.buscarUsuarioPorCPF(usuarioCriarDTO.cpf())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorLogin(usuarioCriarDTO.login())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorEmail(usuarioCriarDTO.email())).thenReturn(Optional.of(usuarioExistente));

        assertThrows(ResourceConflictException.class, () -> usuarioService.criarUsuario(usuarioCriarDTO));
        verify(usuarioRepository, never()).criarUsuario(any());
    }

    @Test
    void atualizarUsuarioBemSucedidoQuandoCredenciaisValidas() {
        UsuarioAtualizarDTO usuarioAtualizarDTO = mockUsuarioAtualizarDTO();

        Usuario usuario = mockUsuarioCliente();

        when(usuarioMapper.usuarioDTOToUsuario(usuarioAtualizarDTO)).thenReturn(usuario);
        when(usuarioRepository.buscarUsuarioPorCPF(usuarioAtualizarDTO.cpf())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorLogin(usuarioAtualizarDTO.login())).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorEmail(usuarioAtualizarDTO.email())).thenReturn(Optional.empty());
        when(usuarioRepository.atualizarUsuario(usuario)).thenReturn(1);

        assertDoesNotThrow(() -> usuarioService.atualizarUsuario(usuarioAtualizarDTO));

        verify(usuarioMapper, times(1)).usuarioDTOToUsuario(usuarioAtualizarDTO);
        verify(usuarioRepository, times(1)).atualizarUsuario(usuario);
    }

    @Test
    void atualizarUsuarioLancaResourceExceptionDefaultQuandoAtualizacaoFalha() {
        UsuarioAtualizarDTO usuarioAtualizarDTO = mockUsuarioAtualizarDTO();

        Usuario usuario = mockUsuarioCliente();

        when(usuarioMapper.usuarioDTOToUsuario(usuarioAtualizarDTO)).thenReturn(usuario);
        when(usuarioRepository.buscarUsuarioPorCPF(anyString())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.buscarPorLogin(anyString())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.buscarPorEmail(anyString())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.atualizarUsuario(usuario)).thenReturn(0);

        assertThrows(ResourceExceptionDefault.class, () -> usuarioService.atualizarUsuario(usuarioAtualizarDTO));
    }

    @Test
    void atualizarUsuarioLancaResourceConflictExceptionQuandoCPFDiferente() {
        UsuarioAtualizarDTO usuarioAtualizarDTO = mockUsuarioAtualizarDTO();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf("12345678901");

        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(2L);

        when(usuarioMapper.usuarioDTOToUsuario(usuarioAtualizarDTO)).thenReturn(usuario);
        when(usuarioRepository.buscarUsuarioPorCPF("12345678901")).thenReturn(Optional.of(outroUsuario));

        assertThrows(ResourceConflictException.class, () -> usuarioService.atualizarUsuario(usuarioAtualizarDTO));
    }

    @Test
    void deletarUsuarioBemSucedidoQuandoUsuarioExiste() {
        UsuarioAtualizarDTO usuarioAtualizarDTO = mockUsuarioAtualizarDTO();

        Usuario usuario = mockUsuarioCliente();

        when(usuarioRepository.buscarUsuarioPorCPF(usuarioAtualizarDTO.cpf())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.deletarUsuario(usuarioAtualizarDTO.id())).thenReturn(1);

        assertDoesNotThrow(() -> usuarioService.deletarUsuario(usuarioAtualizarDTO));

        verify(usuarioRepository, times(1)).buscarUsuarioPorCPF(usuarioAtualizarDTO.cpf());
        verify(usuarioRepository, times(1)).deletarUsuario(1L);
    }

    @Test
    void deletarUsuarioLancaResourceNotFoundExceptionQuandoUsuarioNaoExiste() {
        UsuarioAtualizarDTO usuarioAtualizarDTO = mockUsuarioAtualizarDTO();

        when(usuarioRepository.buscarUsuarioPorCPF(usuarioAtualizarDTO.cpf())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.deletarUsuario(usuarioAtualizarDTO));
        verify(usuarioRepository, never()).deletarUsuario(any());
    }

    @Test
    void deletarUsuarioLancaResourceExceptionDefaultQuandoDeletacaoFalha() {
        UsuarioAtualizarDTO usuarioAtualizarDTO = mockUsuarioAtualizarDTO();

        Usuario usuario = mockUsuarioCliente();

        when(usuarioRepository.buscarUsuarioPorCPF(usuarioAtualizarDTO.cpf())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.deletarUsuario(usuarioAtualizarDTO.id())).thenReturn(0);

        assertThrows(ResourceExceptionDefault.class, () -> usuarioService.deletarUsuario(usuarioAtualizarDTO));
    }

    @Test
    void deletarUsuarioLancaResourceExceptionDefaultQuandoDelecaoRetornaNegativo() {
        UsuarioAtualizarDTO usuarioAtualizarDTO = mockUsuarioAtualizarDTO();

        Usuario usuario = mockUsuarioCliente();

        when(usuarioRepository.buscarUsuarioPorCPF(usuarioAtualizarDTO.cpf())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.deletarUsuario(usuarioAtualizarDTO.id())).thenReturn(-1);

        assertThrows(ResourceExceptionDefault.class, () -> usuarioService.deletarUsuario(usuarioAtualizarDTO));
    }

    @Test
    void atualizarSenhaUsuarioBemSucedidoQuandoCPFExiste() {
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(1, "12345678901", "joao_silva", "novaSenha123");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf("12345678901");

        when(usuarioRepository.buscarUsuarioPorCPF("12345678901")).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.atualizarSenhaUsuario(usuarioSenhaDTO));

        verify(usuarioRepository, times(1)).buscarUsuarioPorCPF("12345678901");
        verify(usuarioRepository, times(1)).atualizarSenhaUsuario(usuarioSenhaDTO);
    }

    @Test
    void atualizarSenhaUsuarioLancaResourceBadRequestExceptionQuandoDTONulo() {
        UsuarioSenhaDTO usuarioSenhaDTO = null;

        assertThrows(ResourceBadRequestException.class, () -> usuarioService.atualizarSenhaUsuario(usuarioSenhaDTO));
        verify(usuarioRepository, never()).buscarUsuarioPorCPF(anyString());
    }

    @Test
    void atualizarSenhaUsuarioLancaResourceBadRequestExceptionQuandoCPFNaoExiste() {
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(1, "12345678901", "joao_silva", "novaSenha123");

        when(usuarioRepository.buscarUsuarioPorCPF("12345678901")).thenReturn(Optional.empty());

        assertThrows(ResourceBadRequestException.class, () -> usuarioService.atualizarSenhaUsuario(usuarioSenhaDTO));
        verify(usuarioRepository, never()).atualizarSenhaUsuario(any());
    }

    @Test
    void atualizarSenhaUsuarioComCPFVazioLancaResourceBadRequestException() {
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(1, "", "joao_silva", "novaSenha123");

        when(usuarioRepository.buscarUsuarioPorCPF("")).thenReturn(Optional.empty());

        assertThrows(ResourceBadRequestException.class, () -> usuarioService.atualizarSenhaUsuario(usuarioSenhaDTO));
    }

    @Test
    void validarLoginUsuarioBemSucedidoQuandoCredenciaisValidas() {
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(1, "12345678901", "joao_silva", "senha123");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("joao_silva");

        when(usuarioRepository.validaUsuarioPorLoginESenha(usuarioSenhaDTO)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.validarLoginUsuario(usuarioSenhaDTO));

        verify(usuarioRepository, times(1)).validaUsuarioPorLoginESenha(usuarioSenhaDTO);
    }

    @Test
    void validarLoginUsuarioLancaResourceBadRequestExceptionQuandoCredenciaisInvalidas() {
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(1, "12345678901", "joao_silva", "senhaErrada");

        when(usuarioRepository.validaUsuarioPorLoginESenha(usuarioSenhaDTO)).thenReturn(Optional.empty());

        assertThrows(ResourceBadRequestException.class, () -> usuarioService.validarLoginUsuario(usuarioSenhaDTO));
        verify(usuarioRepository, times(1)).validaUsuarioPorLoginESenha(usuarioSenhaDTO);
    }

    @Test
    void validarLoginUsuarioComLoginVazioLancaResourceBadRequestException() {
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(1, "12345678901", "", "senha123");

        when(usuarioRepository.validaUsuarioPorLoginESenha(usuarioSenhaDTO)).thenReturn(Optional.empty());

        assertThrows(ResourceBadRequestException.class, () -> usuarioService.validarLoginUsuario(usuarioSenhaDTO));
    }

    @Test
    void validarLoginUsuarioComSenhaVaziaLancaResourceBadRequestException() {
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(1, "12345678901", "joao_silva", "");

        when(usuarioRepository.validaUsuarioPorLoginESenha(usuarioSenhaDTO)).thenReturn(Optional.empty());

        assertThrows(ResourceBadRequestException.class, () -> usuarioService.validarLoginUsuario(usuarioSenhaDTO));
    }

}

