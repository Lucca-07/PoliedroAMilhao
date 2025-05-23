
package com.mycompany.TelaAluno.persistencia;
import com.mycompany.TelaAluno.modelo.Respostas;



public class RespostasDAO {
    
    public Respostas AlternativaPorLetra(int idPergunta, String letra) throws Exception {
    String sql = "SELECT pr.Correta, r.Texto, r.Letra, p.Id_Pergunta FROM Pergunta_Resposta pr JOIN Respostas r ON pr.Id_Resposta = r.Id_Resposta " +
                 "JOIN Perguntas p ON p.Id_Pergunta = pr.Id_Pergunta WHERE r.Letra = ? AND p.Id_Pergunta = ?";

    var fabricaDeConexoes = new ConnectionFactory();
    try (
        var conexao = fabricaDeConexoes.obterConexao();
        var ps = conexao.prepareStatement(sql);
    ) {
        ps.setString(1, letra); // substitui o primeiro ? com o id da alternativa
        ps.setInt(2, idPergunta); // substitui o segundo ? com o id da pergunta
        try (var rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Respostas(
                    rs.getString("Texto"),
                    rs.getString("Letra"),
                    rs.getInt("Id_Pergunta"),
                    rs.getBoolean("Correta")
                );
            }
        }
    }
    return null;
}

    public Respostas AlternativaCorreta(int idPergunta) throws Exception {
    String sql = "SELECT pr.Correta, r.Texto, r.Letra, p.Id_Pergunta FROM Pergunta_Resposta pr JOIN Respostas r ON pr.Id_Resposta = r.Id_Resposta " +
                 "JOIN Perguntas p ON p.Id_Pergunta = pr.Id_Pergunta WHERE pr.Correta = 1 AND p.Id_Pergunta = ?";

    var fabricaDeConexoes = new ConnectionFactory();
    try (
        var conexao = fabricaDeConexoes.obterConexao();
        var ps = conexao.prepareStatement(sql);
    ) {
        ps.setInt(1, idPergunta);
        try (var rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Respostas(
                    rs.getString("Texto"),
                    rs.getString("Letra"),
                    rs.getInt("Id_Pergunta"),
                    rs.getBoolean("Correta")        
                );
            }
        }
    }
    return null; 
}

    
    
}
