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
                ps.setInt(4, pergunta.getId_Materia());

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

    public Perguntas buscarPerguntaMedia(int idMateria) throws Exception {
        Perguntas pergunta = null;

        StringBuilder consultasql = new StringBuilder("SELECT * FROM Perguntas WHERE Dificuldade LIKE 'Média'");

        // Se idMateria for diferente de zero, adiciona o filtro
        if (idMateria != 0) {
            consultasql.append(" AND Id_Materia = ?");
        }

        // Se já existem perguntas usadas, adiciona o filtro para excluir elas
        if (!PontuacaoDAO.idsUsadas.isEmpty()) {
            consultasql.append(" AND Id_Pergunta NOT IN (");
            for (int i = 0; i < PontuacaoDAO.idsUsadas.size(); i++) {
                consultasql.append("?");
                if (i < PontuacaoDAO.idsUsadas.size() - 1) {
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
            for (Integer id : PontuacaoDAO.idsUsadas) {
                ps.setInt(paramIndex++, id);
            }

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    pergunta = new Perguntas(
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
