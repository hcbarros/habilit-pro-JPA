package br.com.habilit_pro.enums;

public enum TipoEmpresa {

    MATRIZ("Matriz"),
    FILIAL("Filial");

    private String nome;

    TipoEmpresa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
