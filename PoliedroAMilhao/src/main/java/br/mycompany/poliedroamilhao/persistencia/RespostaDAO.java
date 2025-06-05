package br.mycompany.poliedroamilhao.persistencia;

import br.mycompany.poliedroamilhao.modelo.Respostas;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RespostaDAO {

    private static final String SQL_BASE = """
        SELECT pr.Correta, r.Texto, r.Letra, p.Id_Pergunta 
        FROM Pergunta_Resposta pr 
        JOIN Respostas r ON pr.Id_Resposta = r.Id_Resposta
        JOIN Perguntas p ON p.Id_Pergunta = pr.Id_Pergunta""";

    public void cadastrasResposta(Respostas r) throws Exception {
        var fabricaDeConexoes = new ConnectionFactory();
        try (var conexao = fabricaDeConexoes.obterConexao(); var ps = conexao.prepareStatement("INSERT INTO Respostas (Correta) VALUES (?)")) {
            ps.setString(1, r.getTexto());
            ps.execute();
        }
    }

    public Respostas AlternativaPorLetra(int idPergunta, String letra) throws Exception {
        String sql = SQL_BASE + " WHERE r.Letra = ? AND p.Id_Pergunta = ?";
        return executarConsultaResposta(sql, idPergunta, letra);
    }

    public Respostas AlternativaCorreta(int idPergunta) throws Exception {
        String sql = SQL_BASE + " WHERE pr.Correta = 1 AND p.Id_Pergunta = ?";
        return executarConsultaResposta(sql, idPergunta, null);
    }

    public List<Respostas> AlternativasIncorretas(int idPergunta) throws Exception {
        List<Respostas> lista = new ArrayList<>();
        String sql = SQL_BASE + " WHERE pr.Correta = 0 AND p.Id_Pergunta = ? LIMIT 3";

        try (var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idPergunta);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(criarResposta(rs));
                }
            }
        }
        return lista;
    }

    private Respostas executarConsultaResposta(String sql, int idPergunta, String letra) throws Exception {
        try (var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {

            if (letra != null) {
                ps.setString(1, letra);
                ps.setInt(2, idPergunta);
            } else {
                ps.setInt(1, idPergunta);
            }

            try (var rs = ps.executeQuery()) {
                return rs.next() ? criarResposta(rs) : null;
            }
        }
    }

    private Respostas criarResposta(ResultSet rs) throws Exception {
        return new Respostas(
                rs.getString("Texto"),
                rs.getString("Letra"),
                rs.getInt("Id_Pergunta"),
                rs.getBoolean("Correta")
        );
    }
}
