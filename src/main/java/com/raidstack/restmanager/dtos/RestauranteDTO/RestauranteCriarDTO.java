package com.raidstack.restmanager.dtos.RestauranteDTO;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record RestauranteCriarDTO(

        @NotNull(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Endereco é obrigatório")
        String endereco,

        @NotNull(message = "O tipo de cozinha é obrigatório")
        String tipoCozinha,

        @NotNull(message = "A hora de início é obrigatória")
        java.sql.Time horaInicio,

        @NotNull(message = "A hora de fim é obrigatória")
        java.sql.Time horaFim

) implements Serializable {
}
