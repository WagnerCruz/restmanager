package com.raidstack.restmanager.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/scripts/create_db.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/scripts/drop_db.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
@Sql(scripts = "/scripts/itemcardapio_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/scripts/itemcardapio_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemCardapioControllerIT {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void todosItensRetornaLista() {
        when()
            .get("/v1/itemcardapio/todos")
        .then()
            .statusCode(200)
            .body("size()", is(2));
    }

    @Test
    void buscarPorNomeRetornaItens() {
        given()
            .queryParam("nome", "Pizza Margherita")
        .when()
            .get("/v1/itemcardapio/nome")
        .then()
            .statusCode(200)
            .body("[0].nome_item", equalTo("Pizza Margherita"));
    }

    @Test
    void buscarPorDescricaoRetornaItens() {
        given()
            .queryParam("descricao", "Pizza clássica")
        .when()
            .get("/v1/itemcardapio/descricao")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void buscarPorDisponibilidadeRetornaItens() {
        given()
                .queryParam("disponibilidade", "Disponível")
                .when()
                .get("/v1/itemcardapio/disponibilidade")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2));
    }

}

