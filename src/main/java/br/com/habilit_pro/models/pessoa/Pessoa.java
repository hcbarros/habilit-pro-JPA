package br.com.habilit_pro.models.pessoa;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@MappedSuperclass
public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome da pessoa não deve ser nulo!")
    private String nome;

    @CPF
    @Column(unique = true)
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
