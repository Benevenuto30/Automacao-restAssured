package com.benevenuto.factory;

import com.benevenuto.pojo.Usuario;

public class UsuarioDataFactory {

    public static Usuario criarUsuarioAdministrador(){
        Usuario usuarioAdministrador = new Usuario();
        usuarioAdministrador.setEmail("admin@email.com");
        usuarioAdministrador.setSenha("654321");
        return usuarioAdministrador;
    }
    public static Usuario criarUsuarioComum(){
        Usuario usuarioComum = new Usuario();
        usuarioComum.setEmail("usuario@email.com");
        usuarioComum.setSenha("123456");
        return usuarioComum;
    }
}
