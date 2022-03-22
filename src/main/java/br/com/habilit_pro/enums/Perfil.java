package br.com.habilit_pro.enums;

public enum Perfil {

    ADMINISTRATIVO("Administrativo"),
    OPERACIONAL("Operacional"),
    RH("RH");

    private String nome;

    Perfil(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}