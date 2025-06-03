package br.mycompany.poliedroamilhao.modelo;

import java.util.*;

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

    public Perguntas(String enunciado, int idMateria){
        this.enunciado = enunciado;
        this.idMateria = idMateria;
        respostas = new ArrayList<>();
    }
    
    public int getIdMateria() {
    return idMateria;
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

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public List<PerguntaResposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<PerguntaResposta> respostas) {
        this.respostas = respostas;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    
}
