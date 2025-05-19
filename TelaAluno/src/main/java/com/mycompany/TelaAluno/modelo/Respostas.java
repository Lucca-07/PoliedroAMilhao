/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.modelo;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor

public class Respostas {
    private int Id_Resposta;
    private boolean Correta;
    private String Alternativa_A;
    private String Alternativa_B;
    private String Alternativa_C;
    private String Alternativa_D;
    private String Alternativa_E;
    private String Alternativa_Correta;
    private int Id_Pergunta;
    
}
