/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.modelo;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor

public class Pergunta {
    private int Id_pergunta;
    private String Enunciado;
    private int Id_Materia;
    private int Id_Premiacao;
    private String Tipo;
    private String Dificuldade;
    
}
