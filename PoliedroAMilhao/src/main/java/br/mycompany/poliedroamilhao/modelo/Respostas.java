
package br.mycompany.poliedroamilhao.modelo;

public class Respostas {
    private int codigo;
    private String texto; 
    
    public Respostas(String texto){
        this.texto = texto;
    }  

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    
}
