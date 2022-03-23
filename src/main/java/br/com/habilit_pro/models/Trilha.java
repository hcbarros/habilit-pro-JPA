package br.com.habilit_pro.models;

import br.com.habilit_pro.enums.Satisfacao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A ocupação não deve ser nula!")
    private String ocupacao;

    @NotNull(message = "O nome da trilha não deve ser nulo!")
    private String nome;

    @NotNull(message = "O apelido da trilha não deve ser nulo!")
    private String apelido;

    @Enumerated(EnumType.STRING)
    private Satisfacao satisfacao;

    private String anotacoes;

    @NotNull(message = "A trilha deve estar associada a uma empresa!")
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "empresa_id_trilha", referencedColumnName = "id")
    private Empresa empresa;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.REMOVE)
    private Set<Modulo> modulos;


    public Trilha() {
        modulos = new HashSet<>();
    }

    public Trilha(Empresa empresa, String ocupacao) {
        this.empresa = empresa;
        this.ocupacao = ocupacao;
        modulos = new HashSet<>();
    }

    public Trilha(Empresa empresa, String ocupacao, Satisfacao satisfacao, String anotacoes) {
        this(empresa, ocupacao);
        this.satisfacao = satisfacao;
        this.anotacoes = anotacoes;
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

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(long count) {
        nome = ocupacao + empresa.getNome() + (count + 1) + LocalDate.now().getYear();
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(long count) {
        apelido = ocupacao + (count + 1);
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

    public Set<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(Set<Modulo> modulos) {
        this.modulos = modulos;
    }

    @Override
    public String toString() {
        return "\nNome da empresa: "+empresa.getNome() +
                "\nCNPJ da empresa: "+empresa.getCnpj() +
                "\nId da trilha: "+id +
                "\nNome da trilha: "+nome +
                "\nApelido da trilha: "+apelido +
                "\nOcupação: "+ocupacao +
                (satisfacao == null ? "" : "\nNível de satisfação: "+ satisfacao.getNivel()) +
                (anotacoes == null ? "" : "\nAnotações: "+ anotacoes);
    }
}