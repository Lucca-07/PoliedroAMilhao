/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.persistencia;

/**
 *
 * @author diego
 */
public class PerguntasMedioDAO {
    public String questao() throws Exception{
        
        String enunciado = null;
                
        var sql = "Select * from Perguntas WHERE Id_Pergunta = 1 AND Dificuldade = Medio";
        
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

    public String resposta_A() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
