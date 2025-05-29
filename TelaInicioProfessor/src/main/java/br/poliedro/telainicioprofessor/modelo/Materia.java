
package br.poliedro.telainicioprofessor.modelo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class Materia {
    private int id;
    private String nome;
    
     public Materia() {
    }

  
            
    public Materia(int id){
        this.id = id;
        
    }
    
public int getId(){return id;}
public void setId(int id){this.id = id; }
    
@Override
public String toString(){
    return nome;
}
 
    
}
