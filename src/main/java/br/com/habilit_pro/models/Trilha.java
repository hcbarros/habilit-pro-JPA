package br.com.habilit_pro.models;

import br.com.habilit_pro.enums.Satisfacao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A ocupação não deve ser nula!")
    private String ocupacao;

    private String nome;

    private String apelido;

    private Satisfacao satisfacao;

    private String anotacoes;

    @NotNull(message = "A trilha deve estar associada a uma empresa!")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;


    public Trilha() { }

    public Trilha(Empresa empresa, String ocupacao, Long count) {
        this.empresa = empresa;
        nome = ocupacao + empresa.getNome() + count + LocalDate.now().getYear();
        apelido = ocupacao + count;
        this.ocupacao = ocupacao;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public String getNome() {
        return nome;
    }

    public String getApelido() {
        return apelido;
    }

    public Satisfacao getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(Satisfacao satisfacao) {
        if(satisfacao != null) {
            this.satisfacao = satisfacao;
        }
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }


    @Override
    public String toString() {
        return "\nNome da empresa: "+empresa.getNome() +
                "\nCNPJ da empresa: "+empresa.getCnpj() +
                "\nNome da trilha: "+nome +
                "\nApelido da trilha: "+apelido +
                "\nOcupação: "+ocupacao +
                (satisfacao == null ? "" : "\nNível de satisfação: "+ satisfacao.getNivel()) +
                (anotacoes == null ? "" : "\nAnotações: "+ anotacoes);
    }
}