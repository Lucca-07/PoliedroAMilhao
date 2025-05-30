
package br.poliedro.telainicioprofessor.modelo;
import lombok.*;

@Getter
@Setter
public class Respostas {
    private int codigo;
    private String texto; 
    
    public Respostas(String texto){
        this.texto = texto;
    }  
}
