package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteAtualizarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteBuscarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteCriarDTO;
import com.raidstack.restmanager.entity.Restaurante;
import com.raidstack.restmanager.mapper.RestauranteMapper;
import com.raidstack.restmanager.repositories.RestauranteRepository;
import com.raidstack.restmanager.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteMapper restauranteMapper;

    public RestauranteService(RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteMapper = restauranteMapper;
    }

    public List<RestauranteBuscarDTO> buscarRestaurantePorNome(String nome) {
        List<Restaurante> restaurantes = restauranteRepository.buscarRestaurantePorNome(nome);
        if(restaurantes.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum restaurante encontrado com o nome: " + nome);
        }
        return restaurantes.stream().map(restauranteMapper::toBuscarDTO).collect(Collectors.toList());
    }

    public List<RestauranteBuscarDTO> buscarTodosRestaurantes() {
        List<Restaurante> restaurantes = restauranteRepository.buscarTodosRestaurantes(10, 1);
        return restaurantes.stream().map(restauranteMapper::toBuscarDTO).collect(Collectors.toList());
    }

    public List<RestauranteBuscarDTO> buscarRestaurantePorTipoCozinha(String tipoCozinha) {
        List<Restaurante> restaurantes = restauranteRepository.buscarRestaurantePorTipoCozinha(tipoCozinha);
        if(restaurantes.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum restaurante encontrado com o tipo de cozinha: " + tipoCozinha);
        }
        return restaurantes.stream().map(restauranteMapper::toBuscarDTO).collect(Collectors.toList());
    }

    public List<RestauranteBuscarDTO> buscarPorHorario(String horaInicio) {
        List<Restaurante> restaurantes = restauranteRepository.buscarPorHorario(java.sql.Time.valueOf(horaInicio));
        if(restaurantes.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum restaurante encontrado com o horário: " + horaInicio);
        }
        return restaurantes.stream().map(restauranteMapper::toBuscarDTO).collect(Collectors.toList());
    }


    public void cadastrarRestaurante(RestauranteCriarDTO restauranteCriarDTO) {
        Restaurante restaurante = restauranteMapper.restauranteCriarDTOToRestaurante(restauranteCriarDTO);
        restauranteRepository.cadastrarRestaurante(restaurante);
    }

    public void atualizarRestaurante(RestauranteAtualizarDTO restauranteAtualizarDTO) {
        Restaurante restaurante = restauranteMapper.restauranteAtualizarDTOToRestaurante(restauranteAtualizarDTO);
        restauranteRepository.atualizarRestaurante(restaurante);
    }

    public void removerRestaurante(RestauranteAtualizarDTO restauranteAtualizarDTO) {
        this.restauranteRepository.buscarPorId(restauranteAtualizarDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com o ID: " + restauranteAtualizarDTO.id()));
        restauranteRepository.deletarRestaurante(restauranteAtualizarDTO.id());
    }


}
