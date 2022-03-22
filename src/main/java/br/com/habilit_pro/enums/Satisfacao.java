package br.com.habilit_pro.enums;

public enum Satisfacao {

    NIVEL_1(1, "Nível_1"),
    NIVEL_2(2, "Nível_2"),
    NIVEL_3(3, "Nível_3"),
    NIVEL_4(4, "Nível_4"),
    NIVEL_5(5, "Nível_5");

    private int nivel;
    private String nome;

    Satisfacao(int nivel, String nome) {
        this.nivel = nivel;
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public String getNome() {
        return nome;
    }

}
