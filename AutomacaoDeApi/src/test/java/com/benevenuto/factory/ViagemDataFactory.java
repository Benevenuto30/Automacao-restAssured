package com.benevenuto.factory;

import com.benevenuto.pojo.Viagem;

public class ViagemDataFactory {

    public static Viagem criarViagem(){
        Viagem viagem = new Viagem();
        viagem.setAcompanhante("Isabela");
        viagem.setDataPartida("2021-05-10");
        viagem.setDataRetorno("2021-05-14");
        viagem.setLocalDeDestino("Santana do Riacho");
        viagem.setRegiao("central");

        return viagem;
    }
}
