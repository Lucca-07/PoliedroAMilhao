
package com.mycompany.TelaAluno.persistencia;

    public class AlunoDAO {
        public String listar()throws Exception{
        
        String nome = null;
        
        var sql = "SELECT * FROM Aluno";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                nome = rs.getString("Nome");
            }
        }
        return nome;
    }
    
    public int buscarPontuacaoPorId(int idAluno) throws Exception {
        String sql = "SELECT Pontuacao FROM Aluno WHERE Id_Aluno = ?";
            try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql);) {
            ps.setInt(1, idAluno);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Pontuacao");
                }
            }
        }
        return 0; 
    }
 
    public void atualizarPontuacao(int idAluno, int novaPontuacao) throws Exception {
        String sql = "UPDATE Aluno SET Pontuacao = ? WHERE Id_Aluno = ?";

        try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql);) {
            ps.setInt(1, novaPontuacao);
            ps.setInt(2, idAluno);
            ps.executeUpdate();
        }
    }
   



}
