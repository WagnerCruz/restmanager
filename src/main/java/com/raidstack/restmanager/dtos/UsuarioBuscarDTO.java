package com.raidstack.restmanager.dtos;

public record UsuarioBuscarDTO(
        Integer id,
        String nome,
        String email,
        String login
) {
}
