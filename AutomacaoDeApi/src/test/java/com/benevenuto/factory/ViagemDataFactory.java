package com.benevenuto.factory;

import com.benevenuto.pojo.Viagem;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;

public class ViagemDataFactory {

    public static Viagem criarViagem() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Viagem viagem = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postV1Viagens.json"), Viagem.class);
        return viagem;
    }
    public static Viagem criarViagemSemLocalDestino() throws IOException {
       Viagem viagemSemLocalDestino = criarViagem();
       viagemSemLocalDestino.setLocalDeDestino("");
       return viagemSemLocalDestino;
    }
}
