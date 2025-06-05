/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.persistencia;
import com.mycompany.TelaAluno.modelo.ControleJogo;
import com.mycompany.TelaAluno.modelo.Pergunta;

public class PerguntasFacilDAO {

    public Pergunta buscarPerguntaFacil(int idMateria) throws Exception {
        Pergunta pergunta = null;

        StringBuilder consultasql = new StringBuilder("SELECT * FROM Perguntas WHERE Dificuldade LIKE 'Fácil'");

        // Se idMateria for diferente de zero, adiciona o filtro
        if (idMateria != 0) {
            consultasql.append(" AND Id_Materia = ?");
        }

        // Se já existem perguntas usadas, adiciona o filtro para excluir elas
        if (!ControleJogo.idsUsadas.isEmpty()) {
            consultasql.append(" AND Id_Pergunta NOT IN (");
            for (int i = 0; i < ControleJogo.idsUsadas.size(); i++) {
                consultasql.append("?");
                if (i < ControleJogo.idsUsadas.size() - 1) {
                    consultasql.append(", ");
                }
            }
            consultasql.append(")");
        }

        consultasql.append(" ORDER BY RAND() LIMIT 1");

        var fabricaDeConexoes = new ConnectionFactory();

        try (var conexao = fabricaDeConexoes.obterConexao(); var ps = conexao.prepareStatement(consultasql.toString())) {

            int paramIndex = 1;

            // Se o filtro de idMateria foi adicionado, setar o parâmetro
            if (idMateria != 0) {
                ps.setInt(paramIndex++, idMateria);
            }

            //  setar os parâmetros da lista idsUsadas
            for (Integer id : ControleJogo.idsUsadas) {
                ps.setInt(paramIndex++, id);
            }

            try (var rs = ps.executeQuery()) {
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
        }

        return pergunta;
    }
       
}
