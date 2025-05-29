
package br.poliedro.telainicioprofessor.modelo;
import lombok.*;
        
@Getter
@Setter
@AllArgsConstructor
public class PerguntaResposta {
    private Perguntas pergunta;
    private Respostas resposta;
    private boolean correta;
  
}
