package br.com.habilit_pro.models.pessoa.trabalhador;

import br.com.habilit_pro.enums.Avaliacao;
import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Modulo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class ModuloTrabalhador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Módulos do trabalhador não devem ser nulos!")
    @OneToOne
    @JoinColumn(name = "modulo_mod_trab_id")
    private Modulo modulo;

    @Enumerated(EnumType.STRING)
    private Avaliacao avaliacao;

    private String anotacao;

    @NotNull(message = "Empresa do trabalhador não deve ser nula!")
    @OneToOne
    @JoinColumn(name = "empresa_mod_trab_id")
    private Empresa empresa;

    private String funcao;

    private String setor;


    public ModuloTrabalhador() { }

    public ModuloTrabalhador(Modulo modulo, Avaliacao avaliacao, String anotacao, Trabalhador t) {
        setModulo(modulo);
        this.avaliacao = avaliacao;
        this.anotacao = anotacao;
        this.empresa = t.getEmpresa();
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getSetor() {
        return setor;
    }

    @Override
    public String toString() {

        return "\nMódulo: "+ modulo.getNome() +
                (avaliacao == null ? "" : "\nAvaliação: "+avaliacao.getNome()) +
                ((anotacao == null || anotacao.isEmpty()) ? "" : "\nAnotação: "+anotacao) +
                "\nEmpresa que oferece o módulo: "+empresa.getNome() +
                "\nFunção exercida durante o múdulo: "+funcao +
                "\nSetor que contém o trabalhador: "+setor;
    }
}