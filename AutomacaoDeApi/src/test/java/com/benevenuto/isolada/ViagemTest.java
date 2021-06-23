package com.benevenuto.isolada;

import com.benevenuto.factory.UsuarioDataFactory;
import com.benevenuto.factory.ViagemDataFactory;
import com.benevenuto.pojo.Usuario;
import com.benevenuto.pojo.Viagem;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class ViagemTest {

    private String token;
    private String tokenUsuarioComum;

    @Before
    public void setup(){
        //configurações rest assured
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        Usuario usuarioAdministrador = UsuarioDataFactory.criarUsuarioAdministrador();

        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuarioAdministrador)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                .path("data.token");

        Usuario usuarioComum = UsuarioDataFactory.criarUsuarioComum();

        this.tokenUsuarioComum = given()
                .contentType(ContentType.JSON)
                .body(usuarioComum)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                .path("data.token");

    }

    @Test
    public void testCadastroDeViagemValidaRetornaSucesso() throws IOException {
       Viagem viagem = ViagemDataFactory.criarViagem();

        given()
                .contentType(ContentType.JSON)
                .body(viagem)
                .header("Authorization", token)
        .when()
                .post("/v1/viagens")
        .then()
            .assertThat().statusCode(201).body("data.localDeDestino",equalToIgnoringCase("Santana do Riacho"));
    }

    @Test
    public void testCadastroDeViagemValidaContrato() throws IOException {
        Viagem viagem = ViagemDataFactory.criarViagem();

        given()
            .contentType(ContentType.JSON)
            .body(viagem)
            .header("Authorization", token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat().statusCode(201).body(matchesJsonSchemaInClasspath("schemas/postV1ViagensViagemValida.json"));
    }

    @Test
    public void testCadastroDeViagemSemLocalDestino() throws IOException {
        Viagem viagem = ViagemDataFactory.criarViagemSemLocalDestino();
        given()
                .contentType(ContentType.JSON)
                .body(viagem)
                .header("Authorization", token)
        .when()
                .post("/v1/viagens")
        .then()
                .log()
                    .all()
                .assertThat().statusCode(400)
                             .body("errors[0].defaultMessage",equalTo("Local de Destino deve estar entre 3 e 40 caracteres"));

    }

    @Test
    public void testConsultaViagem(){
        given()
                .header("Authorization", tokenUsuarioComum)
            .when()
                .get("/v1/viagens/1")
            .then()
                .assertThat().statusCode(200)
                .body("data.acompanhante", equalTo("Isabela"));
    }

    @Test
    public void testProcessaCorretamenteRetornoDaApiDoTempo(){
        given()
                .header("Authorization", tokenUsuarioComum)
            .when()
                .get("/v1/viagens/1")
            .then()
                .assertThat().statusCode(200)
                .body("data.temperatura", equalTo(20f));
    }
}
