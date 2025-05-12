/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.poliedro.telainicioprofessor.persistencia;

import br.poliedro.telainicioprofessor.modelo.Usuario;
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
                            ps2.setString(4, "Ativo");
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
}