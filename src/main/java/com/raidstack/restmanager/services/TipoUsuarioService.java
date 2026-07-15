package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioAtualizarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioBuscarDTO;
import com.raidstack.restmanager.dtos.TipoUsuarioDTO.TipoUsuarioCriarDTO;
import com.raidstack.restmanager.entity.TipoUsuario;
import com.raidstack.restmanager.mapper.TipoUsuarioMapper;
import com.raidstack.restmanager.repositories.TipoUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoUsuarioService {

    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TipoUsuarioMapper tipoUsuarioMapper;

    public TipoUsuarioService(TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.tipoUsuarioMapper = tipoUsuarioMapper;
    }

    public List<TipoUsuarioBuscarDTO> buscarTipoUsuarioPorNome(String nome) {
        return tipoUsuarioRepository.buscarTipoUsuarioPorNome(nome)
                .stream()
                .map(tipoUsuarioMapper::tipoUsuarioToTipoUsuarioBuscarDTO)
                .toList();
    }

    public List<TipoUsuarioBuscarDTO> buscarTodosTipoUsuario(int size, int offset) {
        return tipoUsuarioRepository.buscarTodosTipoUsuario(size, offset)
                .stream()
                .map(tipoUsuarioMapper::tipoUsuarioToTipoUsuarioBuscarDTO)
                .toList();
    }

    public void cadastrarTipoUsuario(TipoUsuarioCriarDTO tipoUsuarioCriarDTO) {
        TipoUsuario tipoUsuario = tipoUsuarioMapper.tipoUsuarioCriarDTOToTipoUsuario(tipoUsuarioCriarDTO);
        tipoUsuarioRepository.criarNovoTipoUsuario(tipoUsuario);
    }

    public void atualizarTipoUsuario(TipoUsuarioAtualizarDTO tipoUsuarioAtualizarDTO) {
        TipoUsuario tipoUsuario = tipoUsuarioMapper.tipoUsuarioAtualizarDTOToTipoUsuario(tipoUsuarioAtualizarDTO);
        tipoUsuarioRepository.atualizarTipoUsuario(tipoUsuario);
    }

    public void deletarTipoUsuario(TipoUsuarioAtualizarDTO tipoUsuarioAtualizarDTO) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.buscarTipoUsuarioPorId(tipoUsuarioAtualizarDTO.id())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tipo de Usuário não encontrado"));
        tipoUsuarioRepository.deletarTipoUsuario(tipoUsuario.getId());
    }

}
