
package br.granado.mavenproject1.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAORanking {
    public List<Integer> obterPontuacoesRanking() throws Exception {
        List<Integer> pontuacoes = new ArrayList<>();
        
        // definir comando sql - ordered by score descending
        String sql = "SELECT Pontuacao FROM Aluno ORDER BY Pontuacao DESC";
        
        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                pontuacoes.add(rs.getInt("Pontuacao"));
            }
        }
        return pontuacoes;
    }
}
