/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.telalogin.modelo;

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
    private boolean check;
    private boolean admin;
    
    
    public Usuario(String e, String s){
        email = e;
        senha = s;
    }
    
    public Usuario(boolean check, boolean admin) {
        this.check = check;
        this.admin = admin;
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