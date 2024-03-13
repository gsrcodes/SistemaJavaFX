package br.fipp.projetosisdental.banco.Entidades;

public class Procedimento {
    private int id;
    private String descricao;
    private int tempo;
    private double valor;

    public Procedimento() {
        this(0,"",0,0);
    }

    public Procedimento(int id, String descricao, int tempo, double valor) {
        this.id = id;
        this.descricao = descricao;
        this.tempo = tempo;
        this.valor = valor;
    }

    public Procedimento(String descricao, int tempo, double valor) {
       this(0,descricao,tempo,valor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
