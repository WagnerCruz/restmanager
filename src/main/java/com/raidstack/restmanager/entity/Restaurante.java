package com.raidstack.restmanager.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table("restaurantes")
public class Restaurante {

    @Id
    private Long id;
    private String nome;
    private String endereco;
    private String tipo_cozinha;
    private Time horario_inicio_functo;
    private Time horario_fim_functo;
    private Integer id_usuario;

}
