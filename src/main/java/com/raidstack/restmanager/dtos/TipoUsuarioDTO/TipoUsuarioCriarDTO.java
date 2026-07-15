package com.raidstack.restmanager.dtos.TipoUsuarioDTO;

import jakarta.validation.constraints.NotNull;

public record TipoUsuarioCriarDTO(

            @NotNull(message = "O nome do tipo de usuário é obrigatório")
            String nome_tipo
) {
}
