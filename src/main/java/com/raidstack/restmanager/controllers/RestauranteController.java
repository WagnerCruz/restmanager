package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteBuscarDTO;
import com.raidstack.restmanager.services.RestauranteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestauranteController.class);
    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public ResponseEntity<List<RestauranteBuscarDTO>> pesquisarTodosRestaurantes() {
        LOGGER.info("Retorna uma lista dos restaurantes cadastrados na base");
        List<RestauranteBuscarDTO> restaurantes = restauranteService.buscarTodosRestaurantes();
        LOGGER.info("Retorno com sucesso de {} restaurantes", restaurantes.size());
        return ResponseEntity.ok(restaurantes);
    }

    @PostMapping("/cadastrarrestaurante")
    public ResponseEntity<Void> cadastrarRestaurante() {
        // Implementation for registering a new restaurant
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
}
