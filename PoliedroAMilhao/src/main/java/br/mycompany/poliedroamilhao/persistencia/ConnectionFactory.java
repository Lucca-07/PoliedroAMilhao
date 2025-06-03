/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.mycompany.poliedroamilhao.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Lucca
 */
public class ConnectionFactory {

    private String host = "mysql-2819f7ac-pi-maua-20251-poliedro.l.aivencloud.com";
    private String port = "15903";
    private String db = "defaultdb";
    private String user = "avnadmin";
    SenhaBD senha = new SenhaBD();
    private String password = senha.setSenha();

    public Connection obterConexao() throws Exception {

        // String de conexão
        var s = String.format(
                "jdbc:mysql://%s:%s/%s",
                host, port, db
        );

        Connection conexao = DriverManager.getConnection(s, user, password);
        return conexao;
    }

    public static void main(String[] args) throws Exception{
        var fabricaDeConexoes = new ConnectionFactory();
        Connection conexao = fabricaDeConexoes.obterConexao();

        if (conexao != null) {
            System.out.println("conectado");
        } else {
            System.out.println("conexão falhou");
        }

    }

}
