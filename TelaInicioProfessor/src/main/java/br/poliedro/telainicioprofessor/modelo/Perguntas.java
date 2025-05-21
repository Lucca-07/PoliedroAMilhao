
package br.poliedro.telainicioprofessor.modelo;
import java.util.*;

public class Perguntas {

    private int codigo;
    private String enunciado;
    private List <PerguntaResposta> respostas;
    
    public Perguntas(String enunciado){
        this.enunciado = enunciado;
        respostas = new ArrayList<>();      
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public List<PerguntaResposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<PerguntaResposta> respostas) {
        this.respostas = respostas;
    }
    
    
  
}
