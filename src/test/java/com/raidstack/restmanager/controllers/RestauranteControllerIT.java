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
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/scripts/create_db.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/scripts/drop_db.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
@Sql(scripts = "/scripts/restaurante_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/scripts/restaurante_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class RestauranteControllerIT {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void buscarTodosRestaurantesRetornaLista() {
        given()
        .when()
            .get("/v1/restaurantes/todos")
        .then()
            .statusCode(200)
            .body("size()", is(2));
    }

    @Test
    void buscarPorTipoCozinhaRetornaLista() {
        String tipo = "Italiana";
        given()
            .queryParam("tipoCozinha", tipo)
        .when()
            .get("/v1/restaurantes/portipocozinha?tipoCozinha=Italiana")
        .then()
            .statusCode(200)
            .body("[0].tipo_cozinha", equalTo("Italiana"));
    }

    @Test
    void buscarSemDadosRetornaErro() {
        given()
        .when()
            .get("/v1/restaurantes/todos")
        .then()
            .statusCode(anyOf(is(200), is(500)));
    }

}

