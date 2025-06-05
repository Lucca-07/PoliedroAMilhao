/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    
    public String host = "mysql-2819f7ac-pi-maua-20251-poliedro.l.aivencloud.com";
    public String port = "15903";
    public String db = "defaultdb";
    public String password = "AVNS_o65xcMELExDiXeXZUyw";
    public String user = "avnadmin";
    
    public Connection obterConexao() throws Exception{
        var s = String.format("jdbc:mysql://%s:%s/%s", host, port, db);
        
        Connection conexao = DriverManager.getConnection(s, user, password);
        return conexao;
    }
    public static void main(String[] args) throws Exception{
        
        var fabricaDeConexoes = new ConnectionFactory();
        var conexao = fabricaDeConexoes.obterConexao();
        
        if(conexao != null){
            System.out.println("conectou!");
        } else {
            System.out.println("Falhou!");
        }
      
    }
}
