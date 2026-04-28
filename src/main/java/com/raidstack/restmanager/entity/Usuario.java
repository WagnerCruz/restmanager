package com.raidstack.restmanager.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Usuario {


    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    private String endereco;
    private int numero;
    private boolean flagProprietario;

}
