package com.raidstack.restmanager.repositories;

import com.raidstack.restmanager.entity.ItemCardapio;
import com.raidstack.restmanager.entity.TipoUsuario;

import java.util.List;

public interface TipoUsuarioRepository {

    List<TipoUsuario> buscarTipoUsuarioPorId(Long id);
    List<TipoUsuario> buscarTodosTipoUsuario(int size, int offset);
    List<TipoUsuario> buscarTipoUsuarioPorNome(String nome);
    Integer criarNovoTipoUsuario(TipoUsuario tipoUsuario);
    Integer atualizarTipoUsuario(TipoUsuario tipoUsuario);
    Integer deletarTipoUsuario(Long id);
}
