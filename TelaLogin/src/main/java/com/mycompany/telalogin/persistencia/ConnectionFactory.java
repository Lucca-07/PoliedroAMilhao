package com.mycompany.telalogin.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private String host = "mysql-2819f7ac-pi-maua-20251-poliedro.l.aivencloud.com";
    private String user = "avnadmin";
    private String port = "15903";
    private String db = "defaultdb";
    SenhaBD password = new SenhaBD();
    private String senha = password.setSenha();

    public Connection obterConexao() throws Exception {
        var s = String.format("jdbc:mysql://%s:%s/%s", host, port, db);

        Connection conexao = DriverManager.getConnection(s, user, senha);
        return conexao;
    }

    public static void main(String[] args) throws Exception {
        var fabricaDeConexoes = new ConnectionFactory();
        Connection conexao = fabricaDeConexoes.obterConexao();

        if (conexao != null) {
            System.out.println("Conectado!");
        } else {
            System.out.println("A conexão falhou!");
        }
    }
}
