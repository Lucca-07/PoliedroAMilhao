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
    public boolean check;
    public boolean admin;
    
    
    public Usuario(boolean logado, boolean administrador){
        check = logado;
        admin = administrador;
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
    
    
}
