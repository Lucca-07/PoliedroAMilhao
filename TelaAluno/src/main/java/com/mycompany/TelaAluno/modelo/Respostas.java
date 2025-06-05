/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.modelo;
import lombok.*;


@Getter
@Setter

public class Respostas {
    private String texto;
    private String letra;
    private int idPergunta;
    private boolean correta;

    public Respostas(String texto, String letra, int idPergunta, boolean correta) {
        this.texto = texto;
        this.letra = letra;
        this.idPergunta = idPergunta;
        this.correta = correta;
    }

    public String getTexto() {
        return texto;
    }

    public String getLetra() {
        return letra;
    }

    public int getIdPergunta() {
        return idPergunta;
    }

    public boolean getCorreta() {
        return correta;
    }
}
