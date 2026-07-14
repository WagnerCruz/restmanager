package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteAtualizarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteBuscarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteCriarDTO;
import com.raidstack.restmanager.services.RestauranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Time;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteControllerTest {

    private RestauranteController controller;

    @Mock
    private RestauranteService service;

    @BeforeEach
    void setUp() {
        controller = new RestauranteController(service);
    }

    @Test
    void pesquisarTodosRestaurantesRetornaOkComLista() {
        RestauranteBuscarDTO dto1 = new RestauranteBuscarDTO("Pizzaria", "Italiana", Time.valueOf("12:00:00"));
        RestauranteBuscarDTO dto2 = new RestauranteBuscarDTO("Churrascaria", "Brasileira", Time.valueOf("18:00:00"));
        when(service.buscarTodosRestaurantes()).thenReturn(Arrays.asList(dto1, dto2));

        ResponseEntity<List<RestauranteBuscarDTO>> response = controller.pesquisarTodosRestaurantes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).buscarTodosRestaurantes();
    }

    @Test
    void pesquisarRestaurantesPorTipoCozinhaRetornaOk() {
        String tipo = "Italiana";
        RestauranteBuscarDTO dto = new RestauranteBuscarDTO("Pizzaria", tipo, Time.valueOf("12:00:00"));
        when(service.buscarRestaurantePorTipoCozinha(tipo)).thenReturn(Arrays.asList(dto));

        ResponseEntity<List<RestauranteBuscarDTO>> response = controller.pesquisarRestaurantesPorTipoCozinha(tipo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).buscarRestaurantePorTipoCozinha(tipo);
    }

    @Test
    void pesquisarRestaurantesPorTipoCozinhaPropagaExcecaoQuandoServicoLanca() {
        String tipo = "Desconhecida";
        when(service.buscarRestaurantePorTipoCozinha(tipo)).thenThrow(new RuntimeException("not found"));

        assertThrows(RuntimeException.class, () -> controller.pesquisarRestaurantesPorTipoCozinha(tipo));
        verify(service, times(1)).buscarRestaurantePorTipoCozinha(tipo);
    }

    @Test
    void pesquisarPorHorarioRetornaOk() {
        String horario = "12:00:00";
        RestauranteBuscarDTO dto = new RestauranteBuscarDTO("Pizzaria", "Italiana", Time.valueOf(horario));
        when(service.buscarPorHorario(horario)).thenReturn(Arrays.asList(dto));

        ResponseEntity<List<RestauranteBuscarDTO>> response = controller.pesquisarRestaurantesPorHorario(horario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).buscarPorHorario(horario);
    }

    @Test
    void cadastrarRestauranteRetornaCreated() {
        RestauranteCriarDTO criarDTO = new RestauranteCriarDTO("Novo", "Endereco", "Italiana", Time.valueOf("11:00:00"), Time.valueOf("23:00:00"));

        ResponseEntity<Void> response = controller.cadastrarRestaurante(criarDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service, times(1)).cadastrarRestaurante(criarDTO);
    }

    @Test
    void atualizarRestauranteRetornaOk() {
        RestauranteAtualizarDTO atualizarDTO = new RestauranteAtualizarDTO(1L, "Nome", "Endereco", "Japonesa", Time.valueOf("10:00:00"), Time.valueOf("22:00:00"));

        ResponseEntity<Void> response = controller.atualizarRestaurante(atualizarDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).atualizarRestaurante(atualizarDTO);
    }

    @Test
    void removerRestauranteRetornaOkQuandoExiste() {
        RestauranteAtualizarDTO atualizarDTO = new RestauranteAtualizarDTO(1L, "Nome", "Endereco", "Italiana", Time.valueOf("10:00:00"), Time.valueOf("22:00:00"));

        ResponseEntity<Void> response = controller.removerRestaurante(atualizarDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).removerRestaurante(atualizarDTO);
    }

    @Test
    void removerRestaurantePropagaExcecaoQuandoServicoLanca() {
        RestauranteAtualizarDTO atualizarDTO = new RestauranteAtualizarDTO(999L, "Nome", "Endereco", "Italiana", Time.valueOf("10:00:00"), Time.valueOf("22:00:00"));
        doThrow(new RuntimeException("not found")).when(service).removerRestaurante(atualizarDTO);

        assertThrows(RuntimeException.class, () -> controller.removerRestaurante(atualizarDTO));
        verify(service, times(1)).removerRestaurante(atualizarDTO);
    }

}

