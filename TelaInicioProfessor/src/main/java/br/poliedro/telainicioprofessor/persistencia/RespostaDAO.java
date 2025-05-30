package br.poliedro.telainicioprofessor.persistencia;
import br.poliedro.telainicioprofessor.modelo.Respostas;
import java.sql.PreparedStatement;

public class RespostaDAO {
    public void cadastrasResposta(Respostas r)throws Exception{
       var fabricaDeConexoes = new ConnectionFactory();
       var conexao = fabricaDeConexoes.obterConexao();
       var sql = "INSERT INTO Respostas (Correta) VALUES (?)";
       PreparedStatement ps = conexao.prepareStatement(sql);
       ps.setString(1, r.getTexto());
       ps.execute();
       ps.close();
       conexao.close();
    }

    }

