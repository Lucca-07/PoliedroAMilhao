/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.persistencia;

import com.mycompany.TelaAluno.modelo.Pergunta;

public class PerguntasMediaDAO {

    public Pergunta buscarPerguntaMedia() throws Exception {
        Pergunta pergunta = null;

        String sql = "SELECT * FROM Perguntas WHERE Dificuldade LIKE 'Média' ORDER BY RAND() LIMIT 1";

        var fabricaDeConexoes = new ConnectionFactory();
        try (
                var conexao = fabricaDeConexoes.obterConexao(); var ps = conexao.prepareStatement(sql); var rs = ps.executeQuery();) {
            if (rs.next()) {
                pergunta = new Pergunta(
                        rs.getInt("Id_Pergunta"),
                        rs.getString("Enunciado"),
                        rs.getInt("Id_Materia"),
                        rs.getInt("Id_Premiacao"),
                        rs.getString("Tipo"),
                        rs.getString("Dificuldade")
                );
            }
        }

        return pergunta;
    }
}
