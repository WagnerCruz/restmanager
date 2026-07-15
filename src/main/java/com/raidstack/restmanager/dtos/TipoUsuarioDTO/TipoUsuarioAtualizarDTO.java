package com.raidstack.restmanager.dtos.TipoUsuarioDTO;

import jakarta.validation.constraints.NotNull;

public record TipoUsuarioAtualizarDTO(

        @NotNull(message = "O ID do tipo de usuário é obrigatório")
        Long id,
        @NotNull(message = "O nome do tipo de usuário é obrigatório")
        String nome_tipo
) {
}