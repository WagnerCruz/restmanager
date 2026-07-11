package com.raidstack.restmanager.dtos.ItemCardapioDTO;

import jakarta.validation.constraints.NotNull;

public record ItemCardapioAtualizarDTO(

        @NotNull(message = "O ID do item é obrigatório")
        Integer id,
        @NotNull(message = "O nome do item é obrigatório")
        String nome_item,
        @NotNull(message = "A descrição do item é obrigatória")
        String descricao,
        @NotNull(message = "O valor do item é obrigatório")
        Double valor_item,
        @NotNull(message = "A disponibilidade do item é obrigatória")
        String disponibilidade,
        @NotNull(message = "A imagem do prato é obrigatória")
        String imagem_prato
) {
}
