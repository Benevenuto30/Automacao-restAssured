package com.benevenuto.isolada;

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

        String token = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"email\": \"admin@email.com\",\n" +
                        "  \"senha\": \"654321\"\n" +
                        "}")
                .when()
                .post("/v1/auth")
                .then()
                .log()
                .all()
                .extract()
                .path("data.token");
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"acompanhante\": \"Isabela\",\n" +
                        "  \"dataPartida\": \"2021-05-10\",\n" +
                        "  \"dataRetorno\": \"2021-05-14\",\n" +
                        "  \"localDeDestino\": \"Lapinha da Serra\",\n" +
                        "  \"regiao\": \"Central\"\n" +
                        "}")
                .header("Authorization", token)
                .when()
                .post("/v1/viagens")
                .then()
                .assertThat().statusCode(201).body("data.localDeDestino",equalToIgnoringCase("Lapinha da Serra"));


    }
}
