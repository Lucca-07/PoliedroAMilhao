package br.granado.mavenproject1.persistencia;

import br.granado.mavenproject1.persistencia.ConnectionFactory;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAORanking {
    public List<Object[]> obterRankingCompleto6() throws Exception {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT Nome, Pontuacao, Serie FROM Aluno WHERE Serie LIKE '%6%' ORDER BY Pontuacao DESC ;" ; // Busca nome e pontuação

        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                String nome = rs.getString("Nome");
                int pontuacao = rs.getInt("Pontuacao");
                ranking.add(new Object[]{nome, pontuacao}); // Adiciona como array (nome, pontuação)
            }
        }
        return ranking;
    }
    public List<Object[]> obterRankingCompleto7() throws Exception {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT Nome, Pontuacao, Serie FROM Aluno WHERE Serie LIKE '%7%' ORDER BY Pontuacao DESC ;"; // Busca nome e pontuação

        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                String nome = rs.getString("Nome");
                int pontuacao = rs.getInt("Pontuacao");
                ranking.add(new Object[]{nome, pontuacao}); // Adiciona como array (nome, pontuação)
            }
        }
        return ranking;
    }public List<Object[]> obterRankingCompleto8() throws Exception {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT Nome, Pontuacao, Serie FROM Aluno WHERE Serie LIKE '%8%' ORDER BY Pontuacao DESC ;"; // Busca nome e pontuação

        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                String nome = rs.getString("Nome");
                int pontuacao = rs.getInt("Pontuacao");
                ranking.add(new Object[]{nome, pontuacao}); // Adiciona como array (nome, pontuação)
            }
        }
        return ranking;
    }public List<Object[]> obterRankingCompleto9() throws Exception {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT Nome, Pontuacao, Serie FROM Aluno WHERE Serie LIKE '%9%' ORDER BY Pontuacao DESC ;"; // Busca nome e pontuação

        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                String nome = rs.getString("Nome");
                int pontuacao = rs.getInt("Pontuacao");
                ranking.add(new Object[]{nome, pontuacao}); // Adiciona como array (nome, pontuação)
            }
        }
        return ranking;
    }
}