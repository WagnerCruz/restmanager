package com.raidstack.restmanager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

public record UsuarioCriarDTO(

        @NotNull(message = "ID do usuário é obrigatório")
        Integer id,

        @NotNull(message = "Nome é obrigatório")
        String nome,

        @CPF(message = "CPF informado é inválido")
        @NotNull(message = "CPF é obrigatório")
        String cpf,

        @Email(message = "E-mail informado é inválido")
        String email,

        @NotNull(message = "Login de usuário é obrigatório")
        String login,

        @NotNull(message = "Senha é obrigatória")
        String senha,

        String endereco,
        Integer numero,
        String flagProprietario

) implements Serializable {



}
