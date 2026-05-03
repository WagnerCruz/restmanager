package com.raidstack.restmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record UsuarioCriarDTO(

        @NotNull(message = "Nome é obrigatório")
        String nome,
        String cpf,
        String email,
        String login,
        String senha,
        String endereco,
        int numero,
        String flagProprietario

) implements Serializable {



}
