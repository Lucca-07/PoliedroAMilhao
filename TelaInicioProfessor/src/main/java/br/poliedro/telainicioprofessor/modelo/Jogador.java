/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.poliedro.telainicioprofessor.modelo;

/**
 *
 * @author Lucca
 */
public class Jogador {
    private int idJogador;
    private String nome;
    private String email;
    private String senha;
    private String serie;
    
    public Jogador(String n, String e, String pass, String s){
        nome = n;
        email = e;
        senha = pass;
        serie = s;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getSerie() {
        return serie;
    }
    
}
