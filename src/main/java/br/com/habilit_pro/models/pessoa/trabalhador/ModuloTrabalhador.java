package br.com.habilit_pro.models.pessoa.trabalhador;

import br.com.habilit_pro.enums.Avaliacao;
import br.com.habilit_pro.models.Modulo;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class ModuloTrabalhador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CPF
    private String cpf;

    @NotNull(message = "A função do trabalhador durante o mulo não deve ser nula!")
    private String funcao;

    @NotNull(message = "O setor que o trabalhador atua durante o módulo não deve ser nulo!")
    private String setor;

    @NotNull(message = "Módulos do trabalhador não devem ser nulos!")
    @OneToOne
    @JoinColumn(name = "modulo_mod_trab_id")
    private Modulo modulo;

    @Enumerated(EnumType.STRING)
    private Avaliacao avaliacao;

    private String anotacao;


    public ModuloTrabalhador() { }

    public ModuloTrabalhador(Modulo modulo, Avaliacao avaliacao, String anotacao) {
        setModulo(modulo);
        this.avaliacao = avaliacao;
        this.anotacao = anotacao;
    }

    public ModuloTrabalhador(Modulo modulo, Avaliacao avaliacao, String anotacao, Trabalhador t) {
        this(modulo, avaliacao, anotacao);
        setCpf(t.getCpf());
        this.funcao = t.getFuncao();
        this.setor = t.getSetor();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public String toString() {

        return "\nMódulo: "+ modulo.getNome() +
                (avaliacao == null ? "" : "\nAvaliação: "+avaliacao.getNome()) +
                ((anotacao == null || anotacao.isEmpty()) ? "" : "\nAnotação: "+anotacao) +
                "\nEmpresa que oferece o módulo: "+modulo.getTrilha().getEmpresa().getNome() +
                "\nCPF do trabalhador: "+cpf +
                "\nFunção exercida durante o múdulo: "+funcao +
                "\nSetor que contém o trabalhador: "+setor;
    }
}