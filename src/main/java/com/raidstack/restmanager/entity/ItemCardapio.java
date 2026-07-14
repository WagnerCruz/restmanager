package com.raidstack.restmanager.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table("item_cardapio")
public class ItemCardapio {

    @Id
    private Long id;
    private String nome_item;
    private String descricao;
    private Double valor_item;
    private String disponibilidade;
    private String imagem_prato;


}
