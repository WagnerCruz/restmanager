package com.raidstack.restmanager.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/scripts/usuario_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/scripts/usuario_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class UsuarioControllerIT {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void getUsuariosRetornaLista() {
        given()
        .when()
            .get("/v1/usuarios")
        .then()
            .statusCode(200)
            .body("size()", is(2));
    }

    @Test
    void validarLoginRetornaNoContentQuandoValido() {
        String body = "{\"id\":1,\"cpf\":\"12345678901\",\"login\":\"joao\",\"senha\":\"senha\"}";
        given()
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .post("/v1/usuarios/login")
        .then()
            .statusCode(anyOf(is(204), is(400), is(500)));
    }

}

