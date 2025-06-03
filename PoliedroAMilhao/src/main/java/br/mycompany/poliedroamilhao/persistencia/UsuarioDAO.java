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
        
        if(!a) { // Cadastro de aluno
            sql = "INSERT INTO Aluno (Nome, Serie) VALUES (?, ?)";
            sql2 = "INSERT INTO login_aluno (Id_Aluno, email, senha, estado) VALUES (?, ?, ?, ?)";
        } else { // Cadastro de professor
            sql = "INSERT INTO Professor (Nome) VALUES (?)";
            sql2 = "INSERT INTO login_professor (id_professor, email, senha) VALUES (?, ?, ?)";
        }

        var fabricaDeConexoes = new ConnectionFactory();
        
        try (var conexao = fabricaDeConexoes.obterConexao()) {
            conexao.setAutoCommit(false);  // Inicia transação
            
            try (var ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                 var ps2 = conexao.prepareStatement(sql2)) {
                
                if(!a) { // Aluno
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
        // Consulta modificada para incluir estado quando for aluno
        String sql = "SELECT 'aluno' as tipo, estado FROM login_aluno WHERE email = ? AND senha = ? "
                + "UNION ALL "
                + "SELECT 'professor' as tipo, NULL as estado FROM login_professor WHERE email = ? AND senha = ?";

        System.out.println("SQL: " + sql);
        System.out.println("Email: " + u.getEmail() + ", Senha: " + u.getSenha());

        try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {

            ps.setString(1, u.getEmail());
            ps.setString(2, u.getSenha());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSenha());

            try (
                    var rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    boolean isAdmin = "professor".equals(tipo);

                    if (!isAdmin) {
                        String estado = rs.getString("estado");
                        boolean ativo = "Ativo".equals(estado);

                        // Atualiza última online
                        String updateSql = "UPDATE login_aluno SET Ultima_online = CURRENT_DATE() WHERE email = ?";

                        // Se inativo, ativa o usuário
                        if (!ativo) {
                            updateSql = "UPDATE login_aluno SET Ultima_online = CURRENT_DATE(), estado = 'Ativo' WHERE email = ?";
                        }

                        try (
                                var ps1 = conexao.prepareStatement(updateSql);) {
                            ps1.setString(1, u.getEmail());
                            ps1.executeUpdate();
                        }
                    }
                    return new Usuario(true, isAdmin);
                }
                return new Usuario(false, false);
            }
        }
    }
}