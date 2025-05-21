
package br.poliedro.telainicioprofessor.modelo;
        
public class PerguntaResposta {
    private Perguntas pergunta;
    private Respostas resposta;
    private boolean correta;

    public boolean isCorreta() {
        return correta;
    }

    public void setCorreta(boolean correta) {
        this.correta = correta;
    }

    public PerguntaResposta(Perguntas pergunta, Respostas resposta, boolean correta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.correta = correta;
    }
    
    
}
