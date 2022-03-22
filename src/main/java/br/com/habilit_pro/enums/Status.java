package br.com.habilit_pro.enums;

public enum Status {

    EM_ANDAMENTO("Curso em andamento"),
    EM_FASE_AVALIACAO("Em fase de avaliação"),
    FINALIZADO("Fase de avaliação finalizada");

    private String nome;

    Status(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static Status getStatus(String nome) {
        for(Status s: values()) {
            if(nome.equals(s.nome)) {
                return s;
            }
        }
        return null;
    }
}
