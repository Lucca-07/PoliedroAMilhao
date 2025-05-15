package br.poliedro.telainicioprofessor.persistencia;
import br.poliedro.telainicioprofessor.modelo.Perguntas;
import java.sql.PreparedStatement;

public class PerguntaDAO{
       public void cadastrarPergunta (Perguntas p) throws Exception{
       var fabricaDeConexoes = new ConnectionFactory();
       var conexao = fabricaDeConexoes.obterConexao();
       var sql = "INSERT INTO Perguntas (Enunciado, Tipo) VALUES (?, ?)";
       PreparedStatement ps = conexao.prepareStatement(sql);
       ps.setString(1, p.getEnunciado());
       ps.setString(2, "Professor");
       ps.execute();
       ps.close();
       conexao.close();
    }

   
}
