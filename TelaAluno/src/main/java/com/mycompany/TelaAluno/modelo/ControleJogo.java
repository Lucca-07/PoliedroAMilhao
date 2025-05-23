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

}
