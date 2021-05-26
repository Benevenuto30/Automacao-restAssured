package com.benevenuto.factory;

import com.benevenuto.pojo.Usuario;

public class UsuarioDataFactory {

    public static Usuario criarUsuarioAdministrador(){
        Usuario usuarioAdministrador = new Usuario();
        usuarioAdministrador.setEmail("admin@email.com");
        usuarioAdministrador.setSenha("654321");
        return usuarioAdministrador;
    }
}
