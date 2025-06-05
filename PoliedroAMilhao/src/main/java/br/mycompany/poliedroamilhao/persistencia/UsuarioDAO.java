/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.mycompany.poliedroamilhao.persistencia;

import br.mycompany.poliedroamilhao.modelo.Usuario;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lucca
 */
public class UsuarioDAO {

    public void cadastrar(Usuario u, boolean a) throws Exception {
        // Define os comandos SQL
        String sql, sql2;

        if (!a) { // Cadastro de aluno
            sql = "INSERT INTO Aluno (Nome, Serie) VALUES (?, ?)";
            sql2 = "INSERT INTO login_aluno (Id_Aluno, email, senha, estado) VALUES (?, ?, ?, ?)";
        } else { // Cadastro de professor
            sql = "INSERT INTO Professor (Nome) VALUES (?)";
            sql2 = "INSERT INTO login_professor (id_professor, email, senha) VALUES (?, ?, ?)";
        }

        var fabricaDeConexoes = new ConnectionFactory();

        try (var conexao = fabricaDeConexoes.obterConexao()) {
            conexao.setAutoCommit(false);  // Inicia transação

            try (var ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); var ps2 = conexao.prepareStatement(sql2)) {

                if (!a) { // Aluno
                    ps.setString(1, u.getNome());
                    ps.setString(2, u.getSerie());
                    ps.executeUpdate();

                    try (var generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            ps2.setString(1, generatedKeys.getString(1));
                            ps2.setString(2, u.getEmail());
                            ps2.setString(3, u.getSenha());
                            ps2.setString(4, "Inativo");
                            ps2.executeUpdate();
                        } else {
                            throw new SQLException("Falha ao obter ID do aluno.");
                        }
                    }
                } else { // Professor
                    ps.setString(1, u.getNome());
                    ps.executeUpdate();

                    try (var generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            ps2.setString(1, generatedKeys.getString(1));
                            ps2.setString(2, u.getEmail());
                            ps2.setString(3, u.getSenha());
                            ps2.executeUpdate();
                        } else {
                            throw new SQLException("Falha ao obter ID do professor.");
                        }
                    }
                }
                conexao.commit();  // Confirma a transação
            } catch (SQLException e) {
                conexao.rollback();  // Desfaz em caso de erro
                throw e;  // Relança a exceção
            } finally {
                conexao.setAutoCommit(true);  // Restaura o auto-commit
            }
        }
    }

    public Usuario logar(Usuario u) throws Exception {
        String nome = null; // Inicializa como null

        String sql = "SELECT 'aluno' as tipo, estado, Nome FROM login_aluno JOIN Aluno USING(Id_Aluno) WHERE email = ? AND senha = ?"
                + "UNION ALL "
                + "SELECT 'professor' as tipo, NULL as estado, Nome FROM login_professor JOIN Professor ON(Professor.Id_Professor=login_professor.id_professor) WHERE email = ? AND senha = ?";

        try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getSenha());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSenha());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    nome = rs.getString("Nome");
                    String tipo = rs.getString("tipo");
                    boolean isAdmin = "professor".equals(tipo);

                    if (!isAdmin) {
                        String estado = rs.getString("estado");
                        boolean ativo = "Ativo".equals(estado);
                        String updateSql = ativo
                                ? "UPDATE login_aluno SET Ultima_online = CURRENT_DATE() WHERE email = ?"
                                : "UPDATE login_aluno SET Ultima_online = CURRENT_DATE(), estado = 'Ativo' WHERE email = ?";

                        try (var ps1 = conexao.prepareStatement(updateSql)) {
                            ps1.setString(1, u.getEmail());
                            ps1.executeUpdate();
                        }
                    }
                    // Retorna com nome nos dois casos
                    return new Usuario(nome, true, isAdmin);
                }
                return new Usuario(nome, false, false);
            }
        }
    }

    public String buscarNomePorId(String nome) throws Exception {
        String sql = "SELECT Nome FROM Aluno WHERE Nome = ?";
        try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql);) {
            ps.setString(1, nome);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Nome");
                }
            }
        }
        return null;
    }

    public int buscarPontuacaoPorNome(String nome) throws Exception {
        String sql = "SELECT Pontuacao FROM Aluno WHERE Nome = ?";
        try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Pontuacao");
                }
            }
        }
        return 0;
    }
    public int buscarPontuacaoPorId(int id) throws Exception {
        String sql = "SELECT PontuacaoTotal FROM Aluno WHERE Pontuacao = ?";
        try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("PontuacaoTotal");
                }
            }
        }
        return 0;
    }

    // Soma os pontos a pontuação total
    public void atualizarPontuacaoTotalSomando(String nome, int pontosNovos) throws Exception {
        String sqlBusca = "SELECT PontuacaoTotal FROM Aluno WHERE Nome = ?";
        String sqlUpdateTotal = "UPDATE Aluno SET PontuacaoTotal = ? WHERE Nome = ?";

        try (
                var conexao = new ConnectionFactory().obterConexao(); var psBusca = conexao.prepareStatement(sqlBusca); var psUpdateTotal = conexao.prepareStatement(sqlUpdateTotal);) {
            psBusca.setString(1, nome);
            try (var rs = psBusca.executeQuery()) {
                if (rs.next()) {
                    int pontuacaoAtual = rs.getInt("PontuacaoTotal");
                    int novaPontuacao = pontuacaoAtual + pontosNovos;

                    psUpdateTotal.setInt(1, novaPontuacao);
                    psUpdateTotal.setString(2, nome);
                    psUpdateTotal.executeUpdate();
                } else {
                    throw new Exception("Aluno com nome = " + nome + " não encontrado.");
                }
            }
        }
    }

    // Atualiza Pontuacao da tabela aluno com   idPremiaaco
    public void atualizarPontuacao(String nome, int idPremiacao) throws Exception {
        String sql = "UPDATE Aluno SET Pontuacao = ? WHERE Nome = ?";
        try (var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idPremiacao);
            ps.setString(2, nome);
            ps.executeUpdate();
        }
    }

    // Garante que existe uma linha na tabela Premiacoes com o id e com o valor 
    public void garantirPremiacaoExiste(int idPremiacao, int valor) throws Exception {
        String sqlBusca = "SELECT Quantidade_Dinheiro FROM Premiacoes WHERE Id_Premiacao = ?";
        String sqlInsert = "INSERT INTO Premiacoes (Id_Premiacao, Quantidade_Dinheiro) VALUES (?, ?)";
        String sqlUpdate = "UPDATE Premiacoes SET Quantidade_Dinheiro = ? WHERE Id_Premiacao = ?";

        try (var conexao = new ConnectionFactory().obterConexao(); var psBusca = conexao.prepareStatement(sqlBusca); var psInsert = conexao.prepareStatement(sqlInsert); var psUpdate = conexao.prepareStatement(sqlUpdate)) {

            psBusca.setInt(1, idPremiacao);
            try (var rs = psBusca.executeQuery()) {
                if (rs.next()) {
                    int valorExistente = rs.getInt("Quantidade_Dinheiro");
                    if (valorExistente != valor) {
                        // Atualiza para o novo valor
                        psUpdate.setInt(1, valor);
                        psUpdate.setInt(2, idPremiacao);
                        psUpdate.executeUpdate();
                    }
                    // se igual, não faz nada
                } else {
                    // Insere novo registro
                    psInsert.setInt(1, idPremiacao);
                    psInsert.setInt(2, valor);
                    psInsert.executeUpdate();
                }
            }
        }
    }

    //Adicionar diretamente os valores de PontuacaoTotal para tabela Premiacoes
    public void sincronizarPontuacoesComPremiacoes() throws Exception {
        String sql = """
        INSERT IGNORE INTO Premiacoes (Id_Premiacao, Quantidade_Dinheiro)
        SELECT DISTINCT PontuacaoTotal, PontuacaoTotal
        FROM Aluno 
        WHERE PontuacaoTotal IS NOT NULL""";

        try (var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
    // Copia os valores de Pontuacaototal para Pontuacao pois assim o ranking pode ser feito

    public void migrarPontuacaoTotalParaPontuacao() throws Exception {
        // Primeiro sincroniza as premiações
        sincronizarPontuacoesComPremiacoes();

        // Depois atualiza apenas os registros onde a pontuação total existe como ID em Premiacoes
        String sqlUpdate = """
        UPDATE Aluno a
        JOIN Premiacoes p ON a.PontuacaoTotal = p.Id_Premiacao
        SET a.Pontuacao = a.PontuacaoTotal
        WHERE a.PontuacaoTotal IS NOT NULL""";

        try (var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sqlUpdate)) {
            ps.executeUpdate();
        }
    }
}
