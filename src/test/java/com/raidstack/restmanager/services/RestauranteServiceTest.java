package com.raidstack.restmanager.services;

import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteAtualizarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteBuscarDTO;
import com.raidstack.restmanager.dtos.RestauranteDTO.RestauranteCriarDTO;
import com.raidstack.restmanager.entity.Restaurante;
import com.raidstack.restmanager.mapper.RestauranteMapper;
import com.raidstack.restmanager.repositories.RestauranteRepository;
import com.raidstack.restmanager.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteServiceTest {

    private RestauranteService restauranteService;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestauranteMapper restauranteMapper;

    @BeforeEach
    void setUp() {
        restauranteService = new RestauranteService(restauranteRepository, restauranteMapper);
    }

    @Test
    void buscarRestaurantePorNomeRetornaListaDTOQuandoNomeExiste() {
        String nome = "Pizzaria Italia";
        Restaurante restaurante1 = criarRestaurante(1L, "Pizzaria Italia", "Italiana");
        Restaurante restaurante2 = criarRestaurante(2L, "Pizzaria Italia Premium", "Italiana");
        List<Restaurante> restaurantes = Arrays.asList(restaurante1, restaurante2);

        RestauranteBuscarDTO dto1 = criarRestauranteBuscarDTO("Pizzaria Italia", "Italiana");
        RestauranteBuscarDTO dto2 = criarRestauranteBuscarDTO("Pizzaria Italia Premium", "Italiana");

        when(restauranteRepository.buscarRestaurantePorNome(nome)).thenReturn(restaurantes);
        when(restauranteMapper.toBuscarDTO(restaurante1)).thenReturn(dto1);
        when(restauranteMapper.toBuscarDTO(restaurante2)).thenReturn(dto2);

        List<RestauranteBuscarDTO> resultado = restauranteService.buscarRestaurantePorNome(nome);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(dto1, resultado.get(0));
        assertEquals(dto2, resultado.get(1));
        verify(restauranteRepository, times(1)).buscarRestaurantePorNome(nome);
        verify(restauranteMapper, times(2)).toBuscarDTO(any(Restaurante.class));
    }

    @Test
    void buscarRestaurantePorNomeLancaResourceNotFoundExceptionQuandoNomeNaoExiste() {
        String nome = "Restaurante Inexistente";
        when(restauranteRepository.buscarRestaurantePorNome(nome)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.buscarRestaurantePorNome(nome));
        verify(restauranteRepository, times(1)).buscarRestaurantePorNome(nome);
        verify(restauranteMapper, never()).toBuscarDTO(any());
    }

    @Test
    void buscarRestaurantePorNomeComNomeVazioLancaResourceNotFoundException() {
        String nome = "";
        when(restauranteRepository.buscarRestaurantePorNome(nome)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.buscarRestaurantePorNome(nome));
    }

    @Test
    void buscarTodosRestaurantesRetornaListaDTOQuandoExistemRestaurantes() {
        Restaurante restaurante1 = criarRestaurante(1L, "Pizzaria A", "Italiana");
        Restaurante restaurante2 = criarRestaurante(2L, "Churrascaria B", "Brasileira");
        List<Restaurante> restaurantes = Arrays.asList(restaurante1, restaurante2);

        RestauranteBuscarDTO dto1 = criarRestauranteBuscarDTO("Pizzaria A", "Italiana");
        RestauranteBuscarDTO dto2 = criarRestauranteBuscarDTO("Churrascaria B", "Brasileira");

        when(restauranteRepository.buscarTodosRestaurantes(10, 1)).thenReturn(restaurantes);
        when(restauranteMapper.toBuscarDTO(restaurante1)).thenReturn(dto1);
        when(restauranteMapper.toBuscarDTO(restaurante2)).thenReturn(dto2);

        List<RestauranteBuscarDTO> resultado = restauranteService.buscarTodosRestaurantes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(restauranteRepository, times(1)).buscarTodosRestaurantes(10, 1);
        verify(restauranteMapper, times(2)).toBuscarDTO(any(Restaurante.class));
    }

    @Test
    void buscarTodosRestaurantesRetornaListaVaziaQuandoNenhumRestauranteCadastrado() {
        when(restauranteRepository.buscarTodosRestaurantes(10, 1)).thenReturn(Collections.emptyList());

        List<RestauranteBuscarDTO> resultado = restauranteService.buscarTodosRestaurantes();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(restauranteRepository, times(1)).buscarTodosRestaurantes(10, 1);
        verify(restauranteMapper, never()).toBuscarDTO(any());
    }

    @Test
    void buscarRestaurantePorTipoCozinhaRetornaListaDTOQuandoTipoExiste() {
        String tipoCozinha = "Italiana";
        Restaurante restaurante1 = criarRestaurante(1L, "Pizzaria A", "Italiana");
        Restaurante restaurante2 = criarRestaurante(2L, "Trattoria B", "Italiana");
        List<Restaurante> restaurantes = Arrays.asList(restaurante1, restaurante2);

        RestauranteBuscarDTO dto1 = criarRestauranteBuscarDTO("Pizzaria A", "Italiana");
        RestauranteBuscarDTO dto2 = criarRestauranteBuscarDTO("Trattoria B", "Italiana");

        when(restauranteRepository.buscarRestaurantePorTipoCozinha(tipoCozinha)).thenReturn(restaurantes);
        when(restauranteMapper.toBuscarDTO(restaurante1)).thenReturn(dto1);
        when(restauranteMapper.toBuscarDTO(restaurante2)).thenReturn(dto2);

        List<RestauranteBuscarDTO> resultado = restauranteService.buscarRestaurantePorTipoCozinha(tipoCozinha);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(restauranteRepository, times(1)).buscarRestaurantePorTipoCozinha(tipoCozinha);
        verify(restauranteMapper, times(2)).toBuscarDTO(any(Restaurante.class));
    }

    @Test
    void buscarRestaurantePorTipoCozinhaLancaResourceNotFoundExceptionQuandoTipoNaoExiste() {
        String tipoCozinha = "Desconhecida";
        when(restauranteRepository.buscarRestaurantePorTipoCozinha(tipoCozinha)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.buscarRestaurantePorTipoCozinha(tipoCozinha));
        verify(restauranteRepository, times(1)).buscarRestaurantePorTipoCozinha(tipoCozinha);
        verify(restauranteMapper, never()).toBuscarDTO(any());
    }

    @Test
    void buscarRestaurantePorTipoCozinhaComTipoVazioLancaResourceNotFoundException() {
        String tipoCozinha = "";
        when(restauranteRepository.buscarRestaurantePorTipoCozinha(tipoCozinha)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.buscarRestaurantePorTipoCozinha(tipoCozinha));
    }

    @Test
    void buscarPorHorarioRetornaListaDTOQuandoRestaurantesAbertosNaHora() {
        String horaInicio = "12:00:00";
        Restaurante restaurante1 = criarRestaurante(1L, "Pizzaria A", "Italiana");
        Restaurante restaurante2 = criarRestaurante(2L, "Restaurante B", "Brasileira");
        List<Restaurante> restaurantes = Arrays.asList(restaurante1, restaurante2);

        RestauranteBuscarDTO dto1 = criarRestauranteBuscarDTO("Pizzaria A", "Italiana");
        RestauranteBuscarDTO dto2 = criarRestauranteBuscarDTO("Restaurante B", "Brasileira");

        when(restauranteRepository.buscarPorHorario(Time.valueOf(horaInicio))).thenReturn(restaurantes);
        when(restauranteMapper.toBuscarDTO(restaurante1)).thenReturn(dto1);
        when(restauranteMapper.toBuscarDTO(restaurante2)).thenReturn(dto2);

        List<RestauranteBuscarDTO> resultado = restauranteService.buscarPorHorario(horaInicio);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(restauranteRepository, times(1)).buscarPorHorario(Time.valueOf(horaInicio));
        verify(restauranteMapper, times(2)).toBuscarDTO(any(Restaurante.class));
    }

    @Test
    void buscarPorHorarioLancaResourceNotFoundExceptionQuandoNenhumRestauranteAberto() {
        String horaInicio = "23:00:00";
        when(restauranteRepository.buscarPorHorario(Time.valueOf(horaInicio))).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.buscarPorHorario(horaInicio));
        verify(restauranteRepository, times(1)).buscarPorHorario(Time.valueOf(horaInicio));
        verify(restauranteMapper, never()).toBuscarDTO(any());
    }

    @Test
    void buscarPorHorarioComHoraVaziaLancaResourceNotFoundException() {
        String horaInicio = "00:00:00";
        when(restauranteRepository.buscarPorHorario(Time.valueOf(horaInicio))).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.buscarPorHorario(horaInicio));
    }

    @Test
    void cadastrarRestauranteBemSucedidoQuandoDTOValido() {
        RestauranteCriarDTO restauranteCriarDTO = criarRestauranteCriarDTO("Novo Restaurante", "Italiana", "11:00:00", "23:00:00");
        Restaurante restaurante = criarRestaurante(null, "Novo Restaurante", "Italiana");

        when(restauranteMapper.restauranteCriarDTOToRestaurante(restauranteCriarDTO)).thenReturn(restaurante);

        assertDoesNotThrow(() -> restauranteService.cadastrarRestaurante(restauranteCriarDTO));

        verify(restauranteMapper, times(1)).restauranteCriarDTOToRestaurante(restauranteCriarDTO);
        verify(restauranteRepository, times(1)).cadastrarRestaurante(restaurante);
    }

    @Test
    void cadastrarRestauranteChamaMapeadorERepositorio() {
        RestauranteCriarDTO restauranteCriarDTO = criarRestauranteCriarDTO("Pizzaria Nova", "Italiana", "12:00:00", "22:00:00");
        Restaurante restaurante = criarRestaurante(1L, "Pizzaria Nova", "Italiana");

        when(restauranteMapper.restauranteCriarDTOToRestaurante(restauranteCriarDTO)).thenReturn(restaurante);

        restauranteService.cadastrarRestaurante(restauranteCriarDTO);

        verify(restauranteMapper, times(1)).restauranteCriarDTOToRestaurante(restauranteCriarDTO);
        verify(restauranteRepository, times(1)).cadastrarRestaurante(restaurante);
    }

    @Test
    void atualizarRestauranteBemSucedidoQuandoDTOValido() {
        RestauranteAtualizarDTO restauranteAtualizarDTO = criarRestauranteAtualizarDTO(1L, "Restaurante Atualizado", "Japonesa", "11:30:00", "23:30:00");
        Restaurante restaurante = criarRestaurante(1L, "Restaurante Atualizado", "Japonesa");

        when(restauranteMapper.restauranteAtualizarDTOToRestaurante(restauranteAtualizarDTO)).thenReturn(restaurante);

        assertDoesNotThrow(() -> restauranteService.atualizarRestaurante(restauranteAtualizarDTO));

        verify(restauranteMapper, times(1)).restauranteAtualizarDTOToRestaurante(restauranteAtualizarDTO);
        verify(restauranteRepository, times(1)).atualizarRestaurante(restaurante);
    }

    @Test
    void atualizarRestauranteChamaMapeadorERepositorio() {
        RestauranteAtualizarDTO restauranteAtualizarDTO = criarRestauranteAtualizarDTO(2L, "Churrascaria Premium", "Brasileira", "10:00:00", "00:00:00");
        Restaurante restaurante = criarRestaurante(2L, "Churrascaria Premium", "Brasileira");

        when(restauranteMapper.restauranteAtualizarDTOToRestaurante(restauranteAtualizarDTO)).thenReturn(restaurante);

        restauranteService.atualizarRestaurante(restauranteAtualizarDTO);

        verify(restauranteMapper, times(1)).restauranteAtualizarDTOToRestaurante(restauranteAtualizarDTO);
        verify(restauranteRepository, times(1)).atualizarRestaurante(restaurante);
    }

    @Test
    void removerRestauranteBemSucedidoQuandoIdExiste() {
        RestauranteAtualizarDTO restauranteAtualizarDTO = criarRestauranteAtualizarDTO(1L, "Restaurante A", "Italiana", "11:00:00", "23:00:00");
        Restaurante restaurante = criarRestaurante(1L, "Restaurante A", "Italiana");

        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        assertDoesNotThrow(() -> restauranteService.removerRestaurante(restauranteAtualizarDTO));

        verify(restauranteRepository, times(1)).buscarPorId(1L);
        verify(restauranteRepository, times(1)).deletarRestaurante(1L);
    }

    @Test
    void removerRestauranteLancaResourceNotFoundExceptionQuandoIdNaoExiste() {
        RestauranteAtualizarDTO restauranteAtualizarDTO = criarRestauranteAtualizarDTO(999L, "Inexistente", "Italiana", "11:00:00", "23:00:00");

        when(restauranteRepository.buscarPorId(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.removerRestaurante(restauranteAtualizarDTO));

        verify(restauranteRepository, times(1)).buscarPorId(999L);
        verify(restauranteRepository, never()).deletarRestaurante(any());
    }

    @Test
    void removerRestauranteLancaResourceNotFoundExceptionComIdZero() {
        RestauranteAtualizarDTO restauranteAtualizarDTO = criarRestauranteAtualizarDTO(0L, "Restaurante", "Italiana", "11:00:00", "23:00:00");

        when(restauranteRepository.buscarPorId(0L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.removerRestaurante(restauranteAtualizarDTO));

        verify(restauranteRepository, never()).deletarRestaurante(any());
    }

    @Test
    void removerRestauranteLancaResourceNotFoundExceptionComIdNegativo() {
        RestauranteAtualizarDTO restauranteAtualizarDTO = criarRestauranteAtualizarDTO((long) -1, "Restaurante", "Italiana", "11:00:00", "23:00:00");

        when(restauranteRepository.buscarPorId(-1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.removerRestaurante(restauranteAtualizarDTO));

        verify(restauranteRepository, never()).deletarRestaurante(any());
    }

    private Restaurante criarRestaurante(Long id, String nome, String tipoCozinha) {
        Restaurante restaurante = new Restaurante();
        restaurante.setId(id);
        restaurante.setNome(nome);
        restaurante.setTipo_cozinha(tipoCozinha);
        return restaurante;
    }

    private RestauranteBuscarDTO criarRestauranteBuscarDTO(String nome, String tipoCozinha) {
        return new RestauranteBuscarDTO(nome, tipoCozinha, Time.valueOf("12:00:00"));
    }

    private RestauranteCriarDTO criarRestauranteCriarDTO(String nome, String tipoCozinha, String horaInicio, String horaFim) {
        return new RestauranteCriarDTO(nome, "Rua Principal", tipoCozinha, Time.valueOf(horaInicio), Time.valueOf(horaFim));
    }

    private RestauranteAtualizarDTO criarRestauranteAtualizarDTO(Long id, String nome, String tipoCozinha, String horaInicio, String horaFim) {
        return new RestauranteAtualizarDTO(id, nome, "Rua Principal", tipoCozinha, Time.valueOf(horaInicio), Time.valueOf(horaFim));
    }

}

