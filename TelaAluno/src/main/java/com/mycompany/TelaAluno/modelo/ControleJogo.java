/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TelaAluno.modelo;
import java.util.List;
import java.util.ArrayList;

public class ControleJogo {

    public static List<Integer> idsUsadas = new ArrayList<>();

    public static void resetarLista() {
        idsUsadas.clear();
    }
    public static void adicionarPerguntaUsada(int idPergunta) {
    idsUsadas.add(idPergunta);
}
    public static int calcularPontuacao(int reinicios) {
        switch (reinicios) {
            case 2:
                return 1250;
            case 3:
                return 2500;
            case 4:
                return 3750;
            case 5:
                return 5000;
            case 6:
                return 10000;
            case 7:
                return 25000;
            case 8:
                return 50000;
            case 9:
                return 100000;
            case 10:
                return 250000;
            case 11:
                return 500000;
            case 12:
                return 750000;
            case 13:
                return 1000000;
            default:
                return 0;
        }
        

    

    }
}
