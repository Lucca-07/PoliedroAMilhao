package br.mycompany.poliedroamilhao.persistencia;

import br.mycompany.poliedroamilhao.modelo.Perguntas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PerguntasMediaDAO {

   public void cadastrarPergunta(Perguntas pergunta) throws Exception {
    var fabricaDeConexoes = new ConnectionFactory();
    var conexao = fabricaDeConexoes.obterConexao();

    try {
        conexao.setAutoCommit(false);

        String sql = "INSERT INTO Perguntas (Enunciado, Tipo, Dificuldade, Id_Materia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pergunta.getEnunciado());
            ps.setString(2, "Professor");
            ps.setString(3, pergunta.getDificuldade());
            ps.setInt(4, pergunta.getIdMateria());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idPergunta = rs.getInt(1);

                    String sqlResposta = "INSERT INTO Respostas (Texto, Letra) VALUES (?, ?)";
                    String sqlPerguntaResposta = "INSERT INTO Pergunta_Resposta (Id_Pergunta, Id_Resposta, Correta) VALUES (?, ?, ?)";

                    String[] letras = {"A", "B", "C", "D", "E"};
                    int index = 0;

                    for (var pr : pergunta.getRespostas()) {
                        try (PreparedStatement psResp = conexao.prepareStatement(sqlResposta, Statement.RETURN_GENERATED_KEYS)) {
                            psResp.setString(1, pr.getResposta().getTexto());
                            psResp.setString(2, letras[index]);
                            psResp.executeUpdate();

                            try (ResultSet rsResp = psResp.getGeneratedKeys()) {
                                if (rsResp.next()) {
                                    int idResposta = rsResp.getInt(1);

                                    try (PreparedStatement psPR = conexao.prepareStatement(sqlPerguntaResposta)) {
                                        psPR.setInt(1, idPergunta);
                                        psPR.setInt(2, idResposta);
                                        psPR.setBoolean(3, pr.isCorreta());
                                        psPR.executeUpdate();
                                    }
                                } else {
                                    throw new Exception("Falha ao obter ID da resposta.");
                                }
                            }
                        }
                        index++;
                    }

                } else {
                    throw new Exception("Falha ao obter ID da pergunta.");
                }
            }
        }

        conexao.commit();

    } catch (Exception e) {
        try {
            conexao.rollback();
        } catch (Exception rollbackEx) {
            System.err.println("Erro no rollback: " + rollbackEx.getMessage());
        }
        throw e;
    } finally {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }
   }
}
