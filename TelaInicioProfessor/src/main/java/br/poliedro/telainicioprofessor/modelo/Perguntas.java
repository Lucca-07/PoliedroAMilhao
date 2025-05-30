package br.poliedro.telainicioprofessor.modelo;

import lombok.*;
import java.util.*;

@Getter
@Setter
public class Perguntas {

    private int codigo;
    private String enunciado;
    private String dificuldade;
    private int idMateria;  // novo campo para armazenar o ID da matéria
    private List<PerguntaResposta> respostas;

    public Perguntas(String enunciado){
        this.enunciado = enunciado;
        respostas = new ArrayList<>();
    }

    // construtor opcional com idMateria, caso queira usar
    public Perguntas(String enunciado, int idMateria){
        this.enunciado = enunciado;
        this.idMateria = idMateria;
        respostas = new ArrayList<>();
    }
    
    public int getIdMateria() {
    return idMateria;
}

}
