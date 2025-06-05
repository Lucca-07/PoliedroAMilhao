
package br.mycompany.poliedroamilhao.modelo;

public class Respostas {
    private String texto;
    private String letra;
    private int codigo;
    private boolean correta;

    public Respostas(String texto, String letra, int codigo, boolean correta) {
        this.texto = texto;
        this.letra = letra;
        this.codigo = codigo;
        this.correta = correta;
    }
    
    public Respostas(String texto){
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public String getLetra() {
        return letra;
    }

    public int getCodigo() {
        return codigo;
    }

    public boolean getCorreta() {
        return correta;
    }

    public boolean isCorreta() {
        return correta;
    }

    public void setCorreta(boolean correta) {
        this.correta = correta;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
     
}
