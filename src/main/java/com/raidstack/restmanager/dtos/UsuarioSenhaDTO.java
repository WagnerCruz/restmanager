package com.raidstack.restmanager.dtos;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

public record UsuarioSenhaDTO(

        Integer id,
        @CPF(message = "CPF informado é inválido")
        String cpf,
        String login,
        String senha

) implements Serializable {}
