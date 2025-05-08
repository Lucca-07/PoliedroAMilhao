/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.poliedro.telainicioprofessor.persistencia;

import br.poliedro.telainicioprofessor.modelo.Jogador;
import java.sql.PreparedStatement;

/**
 *
 * @author Lucca
 */
public class JogadorDAO {
    public void cadastrar(Jogador j) throws Exception{
        // Define o comando SQL
        var sql = "INSERT INTO tb_jogador (nome, email, senha, serie) VALUES(?, ?, ?, ?)";

        // Obtem conexao com o SGBD(MySQL)
        var fabricaDeConexoes = new ConnectionFactory();
        
        // Try with resources
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            // Prepara o comando por meio da conexao
            var ps = conexao.prepareStatement(sql);
        ){
            // Substitui os placeholders
            ps.setString(1, j.getNome());
            ps.setString(2, j.getEmail());
            ps.setString(3, j.getSenha());
            ps.setString(4, j.getSerie());
            
            //Executa
            ps.execute();
        }
        
    }
}
