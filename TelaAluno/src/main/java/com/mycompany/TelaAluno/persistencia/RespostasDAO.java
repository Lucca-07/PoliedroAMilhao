
package com.mycompany.TelaAluno.persistencia;


public class RespostasDAO {
    
    public String resposta_A() throws Exception {
        String Alternativa_A = null;
                
        var sql = "Select Alternativa from Respostas WHERE Id_Resposta = 1";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                Alternativa_A = rs.getString("Alternativa");
            }
        }
        return Alternativa_A;                              
 }   
    
    public String resposta_B() throws Exception {
        String Alternativa_B = null;
                
        var sql = "Select Alternativa from Respostas WHERE Id_Resposta = 2";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                Alternativa_B = rs.getString("Alternativa");
            }
        }
        return Alternativa_B;                              
 }   
    public String resposta_C() throws Exception {
        String Alternativa_C = null;
                
        var sql = "Select Alternativa_C from Respostas WHERE Id_Pergunta = 1";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                Alternativa_C = rs.getString("Alternativa_C");
            }
        }
        return Alternativa_C;                              
 }   
    public String resposta_D() throws Exception {
        String Alternativa_D = null;
                
        var sql = "Select Alternativa_D from Respostas WHERE Id_Pergunta = 1";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                Alternativa_D = rs.getString("Alternativa_D");
            }
        }
        return Alternativa_D;                              
 }   
    public String resposta_E() throws Exception {
        String Alternativa_E = null;
                
        var sql = "Select Alternativa_E from Respostas WHERE Id_Pergunta = 1";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                Alternativa_E = rs.getString("Alternativa_E");
            }
        }
        return Alternativa_E;                              
 }   
    public String resposta_correta() throws Exception {
        String Alternativa_correta = null;
                
        var sql = "Select Alternativa from Respostas WHERE Id_Resposta = 2";
        
        var fabricaDeConexoes = new ConnectionFactory();
        try(
            var conexao = fabricaDeConexoes.obterConexao();
            var ps = conexao.prepareStatement(sql);
            var rs = ps.executeQuery();
        ){
            while(rs.next()){
                Alternativa_correta = rs.getString("Alternativa");
            }
        }
        return Alternativa_correta;                        
 }   
 
    
    
}
