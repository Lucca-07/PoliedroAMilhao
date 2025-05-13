
package br.poliedro.telainicioprofessor.modelo;
import lombok.*;
import java.util.*;

@Getter
@Setter
public class Perguntas {

    private int codigo;
    private String enunciado;
    private List <PerguntaResposta> respostas;
    
    public Perguntas(String enunciado){
        this.enunciado = enunciado;
        respostas = new ArrayList<>();      
    }
  
}
