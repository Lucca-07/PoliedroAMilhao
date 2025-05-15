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
    public Usuario logar(Usuario u) throws Exception{
        boolean check;
        boolean admin;
        
        
        //Definindo o comando MySQL
        var sqlAluno = "SELECT email, senha FROM login_aluno WHERE email = ? AND senha = ?";
        var sqlProfessor = "SELECT email, senha FROM login_professor WHERE email = ? AND senha = ?";
        
        //Estabelecer uma conexao com o SGBD(MySQL)
        //try with resources
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sqlAluno);
        ){
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getSenha());
            try(
                var rs = ps.executeQuery();
            ){
                check = (rs.next());
                admin = false;
            }
        }
        if(!check){
            try(
                var conexao = fabricaDeConexoes.obterConexao();
                var ps = conexao.prepareStatement(sqlProfessor);
            ){
                ps.setString(1, u.getEmail());
                ps.setString(2, u.getSenha());
                try(
                    var rs = ps.executeQuery();
                ){
                    check = (rs.next());
                    admin = true;
                }
            }
        }
        return new Usuario(check, admin);
    }
    
}
