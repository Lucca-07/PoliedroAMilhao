package br.mycompany.poliedroamilhao.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAORanking {

    private static final String SELECT_BASE = "SELECT Nome, Pontuacao, Serie FROM Aluno WHERE Serie LIKE ? ORDER BY Pontuacao DESC";

    public List<Object[]> obterRankingCompleto6() throws Exception {
        return obterRankingPorSerie("%_e_to%");
    }

    public List<Object[]> obterRankingCompleto7() throws Exception {
        return obterRankingPorSerie("%__timo%");
    }

    public List<Object[]> obterRankingCompleto8() throws Exception {
        return obterRankingPorSerie("_ita%");
    }

    public List<Object[]> obterRankingCompleto9() throws Exception {
        return obterRankingPorSerie("%_non_%");
    }

    private List<Object[]> obterRankingPorSerie(String filtroSerie) throws Exception {
        List<Object[]> ranking = new ArrayList<>();

        try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(SELECT_BASE);) {
            ps.setString(1, filtroSerie);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    ranking.add(new Object[]{
                        rs.getString("Nome"),
                        rs.getInt("Pontuacao")
                    });
                }
            }
        }
        return ranking;
    }
}
