package br.mycompany.poliedroamilhao.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PontuacaoDAO {

    public static List<Integer> idsUsadas = new ArrayList<>();

    public static void resetarLista() {
        idsUsadas.clear();
    }

    public static void adicionarPerguntaUsada(int idPergunta) {
        idsUsadas.add(idPergunta);
    }

    public static int calcularPontuacao(int reinicios) {
        return switch (reinicios) {
            case 2 ->
                1250;
            case 3 ->
                2500;
            case 4 ->
                3750;
            case 5 ->
                5000;
            case 6 ->
                10000;
            case 7 ->
                25000;
            case 8 ->
                50000;
            case 9 ->
                100000;
            case 10 ->
                250000;
            case 11 ->
                500000;
            case 12 ->
                750000;
            case 13 ->
                1000000;
            default ->
                0;
        };
    }

    public void iniciarNovaPontuacaoParaAluno(String nome, Connection conexao) throws SQLException {
        String sqlInserirPremiacao = "INSERT INTO Premiacoes (Quantidade_Dinheiro) VALUES (NULL)";
        String sqlAtualizarAluno = "UPDATE Aluno SET Pontuacao = ? WHERE Nome = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sqlInserirPremiacao, Statement.RETURN_GENERATED_KEYS)) {
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idPremiacao = rs.getInt(1);

                    try (PreparedStatement stmtAluno = conexao.prepareStatement(sqlAtualizarAluno)) {
                        stmtAluno.setInt(1, idPremiacao);
                        stmtAluno.setString(2, nome);
                        stmtAluno.executeUpdate();
                    }
                } else {
                    throw new SQLException("ID de premiação não gerado.");
                }
            }
        }
    }
}
