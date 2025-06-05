package br.mycompany.poliedroamilhao.modelo;

import java.util.*;

public class Perguntas {
    private int codigo;
    private String enunciado;
    private int id_Materia;
    private int id_Premiacao;
    private String tipo;
    private String dificuldade;
    private List<PerguntaResposta> respostas;

    
    public Perguntas(String enunciado){
        this.enunciado = enunciado;
        respostas = new ArrayList<>();
    }

    public Perguntas(String enunciado, int id_Materia){
        this.enunciado = enunciado;
        this.id_Materia = id_Materia;
        respostas = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public List<PerguntaResposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<PerguntaResposta> respostas) {
        this.respostas = respostas;
    }
    
    
    
    public int getId_pergunta() {
        return codigo;
    }

    public void setId_pergunta(int codigo) {
        this.codigo = codigo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public int getId_Materia() {
        return id_Materia;
    }

    public void setId_Materia(int id_Materia) {
        this.id_Materia = id_Materia;
    }

    public int getId_Premiacao() {
        return id_Premiacao;
    }

    public void setId_Premiacao(int id_Premiacao) {
        this.id_Premiacao = id_Premiacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Perguntas(int codigo, String enunciado, int id_Materia, int id_Premiacao, String tipo, String dificuldade) {
        this.codigo = codigo;
        this.enunciado = enunciado;
        this.id_Materia = id_Materia;
        this.id_Premiacao = id_Premiacao;
        this.tipo = tipo;
        this.dificuldade = dificuldade;
    }

}
