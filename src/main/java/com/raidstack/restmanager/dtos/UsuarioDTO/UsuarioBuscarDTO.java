package com.raidstack.restmanager.dtos.UsuarioDTO;

public record UsuarioBuscarDTO(
        Integer id,
        String nome,
        String email,
        String login
) {
}
