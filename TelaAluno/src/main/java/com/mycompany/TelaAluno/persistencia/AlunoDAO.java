
package com.mycompany.TelaAluno.persistencia;

import java.sql.Statement;

    public class AlunoDAO {

        public String buscarNomePorId(int idAluno) throws Exception {
            String sql = "SELECT Nome FROM Aluno WHERE Id_Aluno = ?";
            try (
                    var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql);) {
                ps.setInt(1, idAluno);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("Nome");
                    }
                }
            }
            return null;
        }

        public int buscarPontuacaoPorId(int idAluno) throws Exception {
            String sql = "SELECT PontuacaoTotal FROM Aluno WHERE Id_Aluno = ?";
            try (
                    var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
                ps.setInt(1, idAluno);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("PontuacaoTotal");
                    }
                }
            }
            return 0;
        }
        
        // Soma os pontos a pontuação total
        public void atualizarPontuacaoTotalSomando(int idAluno, int pontosNovos) throws Exception {
            String sqlBusca = "SELECT PontuacaoTotal FROM Aluno WHERE Id_Aluno = ?";
            String sqlUpdateTotal = "UPDATE Aluno SET PontuacaoTotal = ? WHERE Id_Aluno = ?";

            try (
                    var conexao = new ConnectionFactory().obterConexao(); var psBusca = conexao.prepareStatement(sqlBusca); var psUpdateTotal = conexao.prepareStatement(sqlUpdateTotal);) {
                psBusca.setInt(1, idAluno);
                try (var rs = psBusca.executeQuery()) {
                    if (rs.next()) {
                        int pontuacaoAtual = rs.getInt("PontuacaoTotal");
                        int novaPontuacao = pontuacaoAtual + pontosNovos;

                        psUpdateTotal.setInt(1, novaPontuacao);
                        psUpdateTotal.setInt(2, idAluno);
                        psUpdateTotal.executeUpdate();
                    } else {
                        throw new Exception("Aluno com ID " + idAluno + " não encontrado.");
                    }
                }
            }
        }

        // Atualiza Pontuacao da tabela aluno com   idPremiaaco
        public void atualizarPontuacao(int idAluno, int idPremiacao) throws Exception {
            String sql = "UPDATE Aluno SET Pontuacao = ? WHERE Id_Aluno = ?";
            try (var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
                ps.setInt(1, idPremiacao);
                ps.setInt(2, idAluno);
                ps.executeUpdate();
            }
        }


        // Garante que existe uma linha na tabela Premiacoes com o id e com o valor 
        public void garantirPremiacaoExiste(int idPremiacao, int valor) throws Exception {
            String sqlBusca = "SELECT Quantidade_Dinheiro FROM Premiacoes WHERE Id_Premiacao = ?";
            String sqlInsert = "INSERT INTO Premiacoes (Id_Premiacao, Quantidade_Dinheiro) VALUES (?, ?)";
            String sqlUpdate = "UPDATE Premiacoes SET Quantidade_Dinheiro = ? WHERE Id_Premiacao = ?";

            try (var conexao = new ConnectionFactory().obterConexao(); 
                    var psBusca = conexao.prepareStatement(sqlBusca); 
                    var psInsert = conexao.prepareStatement(sqlInsert); 
                    var psUpdate = conexao.prepareStatement(sqlUpdate)) {

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
            INSERT INTO Premiacoes (Id_Premiacao, Quantidade_Dinheiro)
            SELECT DISTINCT PontuacaoTotal, PontuacaoTotal
            FROM Aluno WHERE PontuacaoTotal IS NOT NULL AND PontuacaoTotal NOT IN (SELECT Id_Premiacao FROM Premiacoes)""";

            try (
                    var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql)) {
                ps.executeUpdate();
            }
        }
        // Copia os valores de Pontuacaototal para Pontuacao pois assim o ranking pode ser feito
        public void migrarPontuacaoTotalParaPontuacao() throws Exception {
            sincronizarPontuacoesComPremiacoes(); 
            String sqlUpdate = "UPDATE Aluno SET Pontuacao = PontuacaoTotal WHERE PontuacaoTotal IS NOT NULL";

            try (
                    var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sqlUpdate)) {
                ps.executeUpdate();
            }
        }



}
