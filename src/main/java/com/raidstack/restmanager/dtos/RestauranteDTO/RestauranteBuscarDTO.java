package com.raidstack.restmanager.dtos.RestauranteDTO;

import java.sql.Time;

public record RestauranteBuscarDTO(
        String nome,
        String tipoCozinha,
        Time horarioBusca
) {
}
