package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteAtualizarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteBuscarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteCriarDTO;
import com.raidstack.restmanager.services.RestauranteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/todos")
    public ResponseEntity<List<RestauranteBuscarDTO>> pesquisarTodosRestaurantes() {
        LOGGER.info("Retorna uma lista dos restaurantes cadastrados na base");
        List<RestauranteBuscarDTO> restaurantes = restauranteService.buscarTodosRestaurantes();
        LOGGER.info("Retorno com sucesso de {} restaurantes", restaurantes.size());
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/portipocozinha")
    public ResponseEntity<List<RestauranteBuscarDTO>> pesquisarRestaurantesPorTipoCozinha(@RequestParam String tipoCozinha) {
        LOGGER.info("Pesquisando restaurantes do tipo: {}", tipoCozinha);
        List<RestauranteBuscarDTO> restaurantes = restauranteService.buscarRestaurantePorTipoCozinha(tipoCozinha);
        LOGGER.info("Retorno com sucesso de {} restaurantes", restaurantes.size());
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/porhorario")
    public ResponseEntity<List<RestauranteBuscarDTO>> pesquisarRestaurantesPorHorario(@RequestParam String horario) {
        LOGGER.info("Pesquisando restaurantes pelo horário: {}", horario);
        List<RestauranteBuscarDTO> restaurantes = restauranteService.buscarPorHorario(horario);
        LOGGER.info("Retorno com sucesso de {} restaurantes pelo horário informado", restaurantes.size());
        return ResponseEntity.ok(restaurantes);
    }

    @PostMapping("/cadastrarrestaurante")
    public ResponseEntity<Void> cadastrarRestaurante(@Valid @RequestBody RestauranteCriarDTO restauranteCriarDTO) {
        LOGGER.info("Cadastrando novo restaurante: {}", restauranteCriarDTO.nome());
        restauranteService.cadastrarRestaurante(restauranteCriarDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizarrestaurante")
    public ResponseEntity<Void> atualizarRestaurante(@Valid @RequestBody RestauranteAtualizarDTO restauranteAtualizarDTO) {
        LOGGER.info("Atualizando restaurante com ID: {}", restauranteAtualizarDTO.id());
        restauranteService.atualizarRestaurante(restauranteAtualizarDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removerRestaurante(RestauranteAtualizarDTO restauranteAtualizarDTO) {
        restauranteService.removerRestaurante(restauranteAtualizarDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
