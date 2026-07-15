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
@Sql(scripts = {"classpath:schema_restaurante.sql", "classpath:insert_restaurante.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup_restaurante.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class RestauranteControllerIT {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void GET_todos_deveRetornarLista() {
        given()
        .when()
            .get("/v1/restaurantes/todos")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void GET_tipoCozinha_encontrado_e_naoEncontrado() {
        given()
        .when()
            .get("/v1/restaurantes/portipocozinha?tipoCozinha=Italiana")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1));

        given()
        .when()
            .get("/v1/restaurantes/portipocozinha?tipoCozinha=NoType")
        .then()
            .statusCode(404);
    }

    @Test
    void GET_porHorario_encontrado_e_naoEncontrado() {
        given()
        .when()
            .get("/v1/restaurantes/porhorario?horario=12:00:00")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1));

        given()
        .when()
            .get("/v1/restaurantes/porhorario?horario=23:59:59")
        .then()
            .statusCode(404);
    }

    @Test
    void POST_cadastrar_deveRetornarCreated() {
        String json = "{\"nome\":\"NovoR\", \"endereco\":\"E\", \"tipo_cozinha\":\"Japonesa\", \"horario_inicio_functo\":\"11:00:00\", \"horario_fim_functo\":\"22:00:00\", \"id_usuario\":1}";
        given()
            .header("Content-Type", "application/json")
            .body(json)
        .when()
            .post("/v1/restaurantes/cadastrarrestaurante")
        .then()
            .statusCode(201);
    }

    @Test
    void PUT_atualizar_deveRetornarOk() {
        String json = "{\"id\":1, \"nome\":\"R1Up\", \"endereco\":\"E\", \"tipo_cozinha\":\"Italiana\", \"horario_inicio_functo\":\"10:00:00\", \"horario_fim_functo\":\"21:00:00\", \"id_usuario\":1}";
        given()
            .header("Content-Type", "application/json")
            .body(json)
        .when()
            .put("/v1/restaurantes/atualizarrestaurante")
        .then()
            .statusCode(200);
    }

    @Test
    void DELETE_remover_ok_e_notFound() {
        String json = "{\"id\":2}";
        given()
            .header("Content-Type", "application/json")
            .body(json)
        .when()
            .delete("/v1/restaurantes")
        .then()
            .statusCode(200);

        String json2 = "{\"id\":999}";
        given()
            .header("Content-Type", "application/json")
            .body(json2)
        .when()
            .delete("/v1/restaurantes")
        .then()
            .statusCode(404);
    }

}

