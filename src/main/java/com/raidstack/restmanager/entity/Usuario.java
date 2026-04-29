package com.raidstack.restmanager.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table("usuarios")
public class Usuario {

    @Id
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
