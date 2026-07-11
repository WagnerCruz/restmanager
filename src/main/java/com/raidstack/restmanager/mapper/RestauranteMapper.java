package com.raidstack.restmanager.mapper;

import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteAtualizarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteBuscarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteCriarDTO;
import com.raidstack.restmanager.entity.Restaurante;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestauranteMapper {

    RestauranteAtualizarDTO restauranteToRestauranteDTO(Restaurante restaurante);
    Restaurante restauranteCriarDTOToRestaurante(RestauranteCriarDTO restauranteDTO);

    RestauranteBuscarDTO toBuscarDTO(Restaurante restaurante);

    Restaurante restauranteAtualizarDTOToRestaurante(RestauranteAtualizarDTO restauranteAtualizarDTO);
}
