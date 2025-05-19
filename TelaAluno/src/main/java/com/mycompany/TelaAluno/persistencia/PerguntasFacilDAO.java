/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.persistencia;

/**
 *
 * @author diego
 */
public class PerguntasFacilDAO {
    public String questao() throws Exception{
        
        String enunciado = null;
                
        var sql = "Select * from Perguntas WHERE Dificuldade LIKE 'F_cil' AND Id_Pergunta = 1";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                enunciado = rs.getString("Enunciado");
            }
        }
        return enunciado;
    }

  
}

