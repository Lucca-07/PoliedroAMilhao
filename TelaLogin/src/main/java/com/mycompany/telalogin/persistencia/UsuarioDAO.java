/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.telalogin.persistencia;

import com.mycompany.telalogin.modelo.Usuario;


/**
 *
 * @author Lucca
 */
public class UsuarioDAO {
    public Usuario logar(String emailu, String senhau) throws Exception{
        boolean check = false;
        boolean admin = false;
        //Definindo o comando MySQL
        var sql1 = "SELECT email, senha FROM login_aluno";
        var sql2 = "SELECT email, senha FROM login_professor";
        
        //Estabelecer uma conexao com o SGBD(MySQL)
        //try with resources
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps1 = conexao.prepareStatement(sql1);
            var ps2 = conexao.prepareStatement(sql2);
            var rs1 = ps1.executeQuery();
            var rs2 = ps2.executeQuery();
        ){
            while(rs1.next()){
                var email = rs1.getString("email");
                var senha = rs1.getString("senha");
                if(emailu.equals(email) && senhau.equals(senha)){
                    check = true;
                    admin = false;
                }
            }
            if(check == false){
                while (rs2.next()) {
                    var email = rs2.getString("email");
                    var senha = rs2.getString("senha");
                    if (emailu.equals(email) && senhau.equals(senha)) {
                        check = true;
                        admin = true;
                    }
                }
            }
        }
        return new Usuario(check, admin);
    }
    
}
