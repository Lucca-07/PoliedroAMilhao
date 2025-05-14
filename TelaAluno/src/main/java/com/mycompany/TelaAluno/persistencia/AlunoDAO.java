/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.persistencia;


/**
 *
 * @author 25.00475-5
 */
public class AlunoDAO {
    public String listar()throws Exception{
        
        String nome = null;
        
        var sql = "SELECT * FROM Aluno";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                nome = rs.getString("Nome");
            }
        }
        return nome;
    }
}
