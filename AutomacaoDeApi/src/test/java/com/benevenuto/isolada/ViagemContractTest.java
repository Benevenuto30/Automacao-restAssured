package com.benevenuto.isolada;

import com.benevenuto.config.Settings;
import com.benevenuto.factory.UsuarioDataFactory;
import com.benevenuto.factory.ViagemDataFactory;
import com.benevenuto.pojo.Usuario;
import com.benevenuto.pojo.Viagem;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ViagemContractTest {

    private String token;

    @Before
    public void setup(){
        //configurações rest assured
        Settings settings = ConfigFactory.create(Settings.class);

        baseURI = settings.baseURI();
        port = settings.port();
        basePath = settings.basePath();

        Usuario usuarioAdministrador = UsuarioDataFactory.criarUsuarioAdministrador();

        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuarioAdministrador)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                .path("data.token");
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
}
