package com.raidstack.restmanager.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table("cardapio")
public class cardapio {

    private int id;
    private String nome_item;
    private String descricao;
    private double valor_item;
    private String disponibilidade;
    private String imagem_prato;


}
