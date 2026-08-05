package com.raidstack.restmanager.controllers;

import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioAtualizarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioBuscarDTO;
import com.raidstack.restmanager.dtos.ItemCardapioDTO.ItemCardapioCriarDTO;
import com.raidstack.restmanager.mapper.ItemCardapioMapper;
import com.raidstack.restmanager.services.ItemCardapioService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import static com.raidstack.restmanager.helper.MocksHelper.mockItemCardapioAtualizarDTO;
import static com.raidstack.restmanager.helper.MocksHelper.mockItemCardapioCriarDTO;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/scripts/create_db.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/scripts/drop_db.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
@Sql(scripts = "/scripts/itemcardapio_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/scripts/itemcardapio_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemCardapioControllerIT {

    @Autowired
    private ItemCardapioService itemCardapioService;

    @LocalServerPort
    int port;

    private static final String BASE_URL = "/v1/itemcardapio";

    @Autowired
    private ItemCardapioMapper itemCardapioMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void todosItensRetornaLista() {
        when()
                .get(BASE_URL + "/todos")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2));
    }

    @Test
    void buscarPorNomeRetornaItens() {
        given()
                .queryParam("nome", "Pizza Margherita")
                .when()
                .get(BASE_URL + "/nome")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].nome_item", equalTo("Pizza Margherita"))
                .body("[0].descricao", equalTo("Pizza clássica"))
                .body("[0].valor_item", equalTo(35.0F));
    }

    @Test
    void buscarPorDescricaoRetornaItens() {
        given()
                .queryParam("descricao", "Pizza clássica")
                .when()
                .get(BASE_URL + "/descricao")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].nome_item", equalTo("Pizza Margherita"))
                .body("[0].descricao", equalTo("Pizza clássica"))
                .body("[0].valor_item", equalTo(35.0F));
    }

    @Test
    void buscarPorDisponibilidadeRetornaItens() {
        given()
                .queryParam("disponibilidade", "Disponível")
                .when()
                .get(BASE_URL + "/disponibilidade")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(2))
                .body("[0].nome_item", equalTo("Pizza Margherita"))
                .body("[0].descricao", equalTo("Pizza clássica"))
                .body("[0].valor_item", equalTo(35.0F))
                .body("[1].nome_item", equalTo("Pastel de Carne"))
                .body("[1].descricao", equalTo("Pastel crocante"))
                .body("[1].valor_item", equalTo(6.0F));
    }

    @Test
    void cadastrarItemCardapioSemRetorno() {

        ItemCardapioCriarDTO itemCardapio = mockItemCardapioCriarDTO();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemCardapio)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void atualizarItemCardapioSemRetorno() {

        ItemCardapioAtualizarDTO itemCardapio = mockItemCardapioAtualizarDTO(null);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemCardapio)
                .when()
                .put(BASE_URL)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deletarItemCardapioSemRetorno() {

        ItemCardapioBuscarDTO itemCardapioBuscar = this.itemCardapioService.buscarTodosItens(10, 1).getFirst();
        ItemCardapioAtualizarDTO itemCardapio = mockItemCardapioAtualizarDTO(itemCardapioBuscar.id());

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemCardapio)
                .when()
                .delete(BASE_URL)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deletarItemCardapioExceptionItemNaoEncontrado() {

        ItemCardapioAtualizarDTO itemCardapio = mockItemCardapioAtualizarDTO(999999L);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemCardapio)
                .when()
                .delete(BASE_URL)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("detail", equalTo("Item de cardápio não encontrado"));
    }


}

