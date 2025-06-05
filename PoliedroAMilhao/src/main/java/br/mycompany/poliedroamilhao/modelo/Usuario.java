/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.mycompany.poliedroamilhao.modelo;

/**
 *
 * @author Lucca
 */
public class Usuario {

    private int id_usuario;
    private String nome;
    private String email;
    private String senha;
    private String serie;
    private int pontuacao;
    private boolean check;
    private boolean admin;
    
    public Usuario(String e, String s){
        senha = s;
        email = e;
    }
    
    public Usuario(int id_usuario, String nome, String serie, int pontuacao){
        this.id_usuario = id_usuario;
        this.nome = nome;
        this.serie = serie;
        this.pontuacao = pontuacao;
    }
    
    public Usuario(String n, String e, String s){
        nome = n;
        email = e;
        senha = s;
    }
    public Usuario(String n, String e, String s, String se){
        nome = n;
        email = e;
        senha = s;
        serie = se;
    }
    
    public Usuario(boolean check, boolean admin) {
        this.check = check;
        this.admin = admin;
    }
    
    public Usuario(String n, boolean check, boolean admin) {
        nome = n;
        this.check = check;
        this.admin = admin;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    
    
    public int getId_usuario() {
        return id_usuario;
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

    public boolean isCheck() {
        return check;
    }

    public boolean isAdmin() {
        return admin;
    }
    
    
}