package com.raidstack.restmanager.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema_tipo_usuario.sql", "classpath:insert_tipo_usuario.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup_tipo_usuario.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class TipoUsuarioControllerIT {

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void GET_todos_deveRetornarLista() {
        given()
        .when()
            .get("/v1/tipousuario/todos")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void GET_nome_deveRetornarEncontrado_eVazio() {
        given()
        .when()
            .get("/v1/tipousuario/nome?nome=Admin")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1));

        given()
        .when()
            .get("/v1/tipousuario/nome?nome=NoSuchName")
        .then()
            .statusCode(200)
            .body("size()", is(0));
    }

    @Test
    void POST_criar_deveRetornarCreated() {
        String json = "{\"nome_tipo\":\"NovoTipo\"}";
        given()
            .header("Content-Type", "application/json")
            .body(json)
        .when()
            .post("/v1/tipousuario")
        .then()
            .statusCode(201);
    }

    @Test
    void PUT_atualizar_deveRetornarOk() {
        String json = "{\"id\":1, \"nome_tipo\":\"AdminUpdated\"}";
        given()
            .header("Content-Type", "application/json")
            .body(json)
        .when()
            .put("/v1/tipousuario")
        .then()
            .statusCode(200);
    }

    @Test
    void DELETE_deletar_deveRetornarOk() {
        String json = "{\"id\":2, \"nome_tipo\":\"ToDelete\"}";
        given()
            .header("Content-Type", "application/json")
            .body(json)
        .when()
            .delete("/v1/tipousuario")
        .then()
            .statusCode(200);
    }

    @Test
    void DELETE_deletar_naoEncontrado_deveRetornar500() {
        String json = "{\"id\":9999, \"nome_tipo\":\"X\"}";
        given()
            .header("Content-Type", "application/json")
            .body(json)
        .when()
            .delete("/v1/tipousuario")
        .then()
            .statusCode(500);
    }

    @Test
    @Sql(scripts = "classpath:cleanup_tipo_usuario.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void endpoints_semBanco_deveRetornar500() {
        given()
        .when()
            .get("/v1/tipousuario/todos")
        .then()
            .statusCode(500);
    }
}
