
package br.mycompany.poliedroamilhao.modelo;

public class PerguntaResposta {

    private Perguntas pergunta;
    private Respostas resposta;
    private boolean correta;

    public PerguntaResposta(Perguntas pergunta, Respostas resposta, boolean correta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.correta = correta;
    }
    
    public Perguntas getPergunta() {
        return pergunta;
    }

    public Respostas getResposta() {
        return resposta;
    }

    public boolean isCorreta() {
        return correta;
    }

    public void setPergunta(Perguntas pergunta) {
        this.pergunta = pergunta;
    }

    public void setResposta(Respostas resposta) {
        this.resposta = resposta;
    }

    public void setCorreta(boolean correta) {
        this.correta = correta;
    }
  
    
}
