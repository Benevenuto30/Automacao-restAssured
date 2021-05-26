package com.benevenuto.isolada;

import com.benevenuto.factory.UsuarioDataFactory;
import com.benevenuto.factory.ViagemDataFactory;
import com.benevenuto.pojo.Usuario;
import com.benevenuto.pojo.Viagem;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class ViagemTest {
    @Test
    public void testCadastroDeViagemValidaRetornaSucesso(){
        //configurações rest assured
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        Usuario usuarioAdministrador = UsuarioDataFactory.criarUsuarioAdministrador();

        String token = given()
                .contentType(ContentType.JSON)
                .body(usuarioAdministrador)
                .when()
                .post("/v1/auth")
                .then()
                .log()
                .all()
                .extract()
                .path("data.token");

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
}
