package com.raidstack.restmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record UsuarioSenhaDTO(

        Integer id,
        String cpf,
        String login,
        String senha

) implements Serializable {



}
