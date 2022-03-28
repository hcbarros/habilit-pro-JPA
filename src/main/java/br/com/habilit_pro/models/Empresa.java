package br.com.habilit_pro.models;

import br.com.habilit_pro.enums.Regional;
import br.com.habilit_pro.enums.Segmento;
import br.com.habilit_pro.enums.TipoEmpresa;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome da empresa não deve ser nulo!")
    @Pattern(regexp = "(?=.*[a-zA-Z]).[0-9a-zA-Z$*&@#_\\-.\\wÀ-ú ]+",
            message="A empresa deve ter um nome!")
    private String nome;

    @CNPJ
    @Column(unique = true)
    private String cnpj;

    @NotNull(message = "O tipo da empresa não deve ser nulo!")
    @Enumerated(EnumType.STRING)
    private TipoEmpresa tipo;

    private String nomeFilial;

    @NotNull(message = "O segmento da empresa não deve ser nulo!")
    @Enumerated(EnumType.STRING)
    private Segmento segmento;

    @NotNull(message = "O estado da empresa não deve ser nulo!")
    @Pattern(regexp = "[a-zA-Z]{2,}",
            message="Informe o estado onde fica a empresa!")
    private String estado;

    @NotNull(message = "A cidade da empresa não deve ser nula!")
    @Pattern(regexp = "(?=.*[a-zA-Z]).[0-9a-zA-Z$*&@#_\\-.\\wÀ-ú ]+",
            message="Informe a cidade onde fica a empresa!")
    private String cidade;

    @NotNull(message = "A regional da empresa não deve ser nula!")
    @Enumerated(EnumType.STRING)
    private Regional regional;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.REMOVE)
    private Set<Trabalhador> trabalhadores;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    private Set<Trilha> trilhas;


    public Empresa() {
        trilhas = new HashSet<>();
    }

    public Empresa(String nome, String cnpj, TipoEmpresa tipo, String nomeFilial,
                   Segmento segmento, String estado, String cidade, Regional regional) {

        this.nome = nome;
        setCnpj(cnpj);
        this.tipo = tipo;
        setNomeFilial(nomeFilial);
        this.segmento = segmento;
        this.estado = estado;
        this.cidade = cidade;
        this.regional = regional;
        trilhas = new HashSet<>();
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if(cnpj != null && (cnpj = cnpj.replaceAll("[^\\d]", "")).matches("\\d{14}")) {
            this.cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." +
                        cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" +
                        cnpj.substring(12, 14);
        }
    }

    public TipoEmpresa getTipo() {
        return tipo;
    }

    public void setTipo(TipoEmpresa tipo) {
        this.tipo = tipo;
    }

    public String getNomeFilial() {
        return nomeFilial;
    }

    public void setNomeFilial(String nomeFilial) {
        if (tipo == TipoEmpresa.FILIAL) {
            this.nomeFilial = nomeFilial;
        }
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Regional getRegional() {
        return regional;
    }

    public void setRegional(Regional regional) {
        this.regional = regional;
    }

    public Set<Trabalhador> getTrabalhadores() {
        return trabalhadores;
    }

    public void setTrabalhadores(Set<Trabalhador> trabalhadores) {
        this.trabalhadores = trabalhadores;
    }

    public Set<Trilha> getTrilhas() {
        return trilhas;
    }

    public void setTrilhas(Set<Trilha> trilhas) {
        this.trilhas = trilhas;
    }


    @Override
    public String toString() {
        return  "\nId: " + id +
                "\nNome: " + nome +
                "\nDocumento: " + cnpj +
                "\nTipo da empresa: " + tipo.getNome() +
                (tipo == TipoEmpresa.FILIAL ? "\nNome da filial: " + nomeFilial : "") +
                "\nSegmento: " + segmento.getNome() +
                "\nEstado: " + estado +
                "\nCidade: " + cidade +
                "\nRegional: " + regional.getNome();
    }

}