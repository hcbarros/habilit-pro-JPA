package br.com.habilit_pro.models;

import br.com.habilit_pro.annotations.CPF;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @CPF
    private String cpf;

    public Pessoa() { }

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        setCpf(cpf);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if(cpf != null && (cpf = cpf.replaceAll("[^\\d]", "")).matches("\\d{11}")) {
            this.cpf = cpf.substring(0,3)+"."+ cpf.substring(3,6)+"."+
                       cpf.substring(6,9)+"-"+cpf.substring(9,11);
        }
    }

}
