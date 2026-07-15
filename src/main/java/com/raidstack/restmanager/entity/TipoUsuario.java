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
@Table("tipo_usuario")
public class TipoUsuario {


    @Id
    private Long id;
    private String nome_tipo_usuario;
}
