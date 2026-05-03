package com.raidstack.restmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record UsuarioSenhaDTO(

        Integer id,
        String cpf,
        String login,
        @NotNull(message = "Senha é obrigatória")
        String senha

) implements Serializable {



}
