package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioBuscarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioCriarDTO;
import com.raidstack.restmanager.entity.TipoUsuario;
import com.raidstack.restmanager.mapper.TipoUsuarioMapper;
import com.raidstack.restmanager.repositories.TipoUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioServiceTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Mock
    private TipoUsuarioMapper tipoUsuarioMapper;

    @InjectMocks
    private TipoUsuarioService tipoUsuarioService;

    @Test
    void buscarTipoUsuarioPorNome_deveRetornarLista() {
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        TipoUsuarioBuscarDTO dto = mock(TipoUsuarioBuscarDTO.class);

        when(tipoUsuarioRepository.buscarTipoUsuarioPorNome("admin")).thenReturn(List.of(tipoUsuario));
        when(tipoUsuarioMapper.tipoUsuarioToTipoUsuarioBuscarDTO(tipoUsuario)).thenReturn(dto);

        List<TipoUsuarioBuscarDTO> resultado = tipoUsuarioService.buscarTipoUsuarioPorNome("admin");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertSame(dto, resultado.get(0));
        verify(tipoUsuarioRepository).buscarTipoUsuarioPorNome("admin");
        verify(tipoUsuarioMapper).tipoUsuarioToTipoUsuarioBuscarDTO(tipoUsuario);
    }

    @Test
    void buscarTodosTipoUsuario_deveRetornarLista() {
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        TipoUsuarioBuscarDTO dto = mock(TipoUsuarioBuscarDTO.class);

        when(tipoUsuarioRepository.buscarTodosTipoUsuario(5, 2)).thenReturn(List.of(tipoUsuario));
        when(tipoUsuarioMapper.tipoUsuarioToTipoUsuarioBuscarDTO(tipoUsuario)).thenReturn(dto);

        List<TipoUsuarioBuscarDTO> resultado = tipoUsuarioService.buscarTodosTipoUsuario(5, 2);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertSame(dto, resultado.get(0));
        verify(tipoUsuarioRepository).buscarTodosTipoUsuario(5, 2);
        verify(tipoUsuarioMapper).tipoUsuarioToTipoUsuarioBuscarDTO(tipoUsuario);
    }

    @Test
    void cadastrarTipoUsuario_deveChamarRepositorio() {
        TipoUsuarioCriarDTO criarDTO = mock(TipoUsuarioCriarDTO.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        when(tipoUsuarioMapper.tipoUsuarioCriarDTOToTipoUsuario(criarDTO)).thenReturn(tipoUsuario);
        when(tipoUsuarioRepository.criarNovoTipoUsuario(tipoUsuario)).thenReturn(1);

        tipoUsuarioService.cadastrarTipoUsuario(criarDTO);

        verify(tipoUsuarioMapper).tipoUsuarioCriarDTOToTipoUsuario(criarDTO);
        verify(tipoUsuarioRepository).criarNovoTipoUsuario(tipoUsuario);
    }

    @Test
    void atualizarTipoUsuario_deveChamarRepositorio() {
        TipoUsuarioAtualizarDTO atualizarDTO = mock(TipoUsuarioAtualizarDTO.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        when(tipoUsuarioMapper.tipoUsuarioAtualizarDTOToTipoUsuario(atualizarDTO)).thenReturn(tipoUsuario);
        when(tipoUsuarioRepository.atualizarTipoUsuario(tipoUsuario)).thenReturn(1);

        tipoUsuarioService.atualizarTipoUsuario(atualizarDTO);

        verify(tipoUsuarioMapper).tipoUsuarioAtualizarDTOToTipoUsuario(atualizarDTO);
        verify(tipoUsuarioRepository).atualizarTipoUsuario(tipoUsuario);
    }

    @Test
    void deletarTipoUsuario_deveDeletarQuandoEncontrado() {
        TipoUsuarioAtualizarDTO atualizarDTO = mock(TipoUsuarioAtualizarDTO.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        when(atualizarDTO.id()).thenReturn(10L);
        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(10L)).thenReturn(List.of(tipoUsuario));
        when(tipoUsuario.getId()).thenReturn(10L);
        when(tipoUsuarioRepository.deletarTipoUsuario(10L)).thenReturn(1);

        tipoUsuarioService.deletarTipoUsuario(atualizarDTO);

        verify(tipoUsuarioRepository).buscarTipoUsuarioPorId(10L);
        verify(tipoUsuarioRepository).deletarTipoUsuario(10L);
    }

    @Test
    void deletarTipoUsuario_deveLancarExceptionQuandoNaoEncontrado() {
        TipoUsuarioAtualizarDTO atualizarDTO = mock(TipoUsuarioAtualizarDTO.class);

        when(atualizarDTO.id()).thenReturn(99L);
        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(99L)).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> tipoUsuarioService.deletarTipoUsuario(atualizarDTO));
        assertEquals("Tipo de Usuário não encontrado", ex.getMessage());

        verify(tipoUsuarioRepository).buscarTipoUsuarioPorId(99L);
        verify(tipoUsuarioRepository, never()).deletarTipoUsuario(anyLong());
    }
}
