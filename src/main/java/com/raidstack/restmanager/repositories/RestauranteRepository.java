package com.raidstack.restmanager.repositories;

import com.raidstack.restmanager.entity.Restaurante;

import java.sql.Time;
import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> buscarRestaurantePorNome(String nome);
    List<Restaurante> buscarTodosRestaurantes(int size, int offset);
    List<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha);
    List<Restaurante> buscarPorHorario(Time horaInicio);
    Integer cadastrarRestaurante(Restaurante restaurante);
    Integer atualizarRestaurante(Restaurante restaurante);
    Integer deletarRestaurante(Long id);

}
