/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.telalogin.persistencia;

import com.mycompany.telalogin.modelo.Usuario;

/**
 *
 * @author Lucca
 */
public class UsuarioDAO {
    public Usuario logar(Usuario u) throws Exception {
        String sql = "SELECT 'aluno' as tipo FROM login_aluno WHERE email = ? AND senha = ? "
                + "UNION ALL "
                + "SELECT 'professor' as tipo FROM login_professor WHERE email = ? AND senha = ?";

        System.out.println("SQL: " + sql); // Debug 1
        System.out.println("Email: " + u.getEmail() + ", Senha: " + u.getSenha()); // Debug 2

        try (
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql)
            ) {

            ps.setString(1, u.getEmail());
            ps.setString(2, u.getSenha());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSenha());

            try (
                    var rs = ps.executeQuery()
                ) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    boolean isAdmin = "professor".equals(tipo);
                    return new Usuario(true, isAdmin);
                }
                return new Usuario(false, false);
            }
        }
    }
}