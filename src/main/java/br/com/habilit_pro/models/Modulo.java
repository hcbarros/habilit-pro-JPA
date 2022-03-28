package br.com.habilit_pro.models;

import br.com.habilit_pro.enums.Status;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.OffsetDateTime;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Modulo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A nome do módulo não deve ser nulo!")
    @Pattern(regexp = "(?=.*[a-zA-Z]).[0-9a-zA-Z$*&@#_\\-.\\wÀ-ú ]+",
            message="Informe o nome do módulo!")
    private String nome;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> habilidades;

    private String tarefaValidacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int prazo_limite = 10;

    private OffsetDateTime inicioAvaliacao;

    private OffsetDateTime inicioModulo;

    private OffsetDateTime fimModulo;

    @NotNull(message = "O módulo deve estar associado a uma trilha!")
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "trilha_id_modulo", referencedColumnName = "id")
    private Trilha trilha;

    @OneToOne(mappedBy = "modulo", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private ModuloTrabalhador moduloTrabalhador;


    public Modulo() {
        habilidades = new HashSet<>();
    }

    public Modulo(Trilha trilha, String nome, String tarefaValidacao, String ...habilidades) {
        this.trilha = trilha;
        this.nome = nome;
        setTarefaValidacao(tarefaValidacao);
        this.habilidades = new HashSet<>();
        definirHabilidades(true, habilidades);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trilha getTrilha() {
        return trilha;
    }

    public void setTrilha(Trilha trilha) {
        this.trilha = trilha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if(status == Status.EM_FASE_AVALIACAO) {
            setFimModulo(OffsetDateTime.now());
        }
        else if(status == Status.EM_ANDAMENTO) {
            setInicioModulo(OffsetDateTime.now());
        }
        else this.status = status;
    }

    public String getTarefaValidacao() {
        return tarefaValidacao;
    }

    public void setTarefaValidacao(String tarefaValidacao) {
        this.tarefaValidacao = tarefaValidacao;
    }

    public Set<String> getHabilidades() {
        return habilidades;
    }

    public void definirHabilidades(boolean add, String ...habilidades) {
        if(habilidades != null) {
            if(add) {
                this.habilidades.addAll(Arrays.asList(habilidades));
                return;
            }
            this.habilidades.removeAll(Arrays.asList(habilidades));
        }
    }

    public int getPrazo_limite() {
        return prazo_limite;
    }

    public void setPrazo_limite(int prazo_limite) {
        this.prazo_limite = prazo_limite;
    }

    public OffsetDateTime getInicioAvaliacao() {
        return inicioAvaliacao;
    }

    public OffsetDateTime getInicioModulo() {
        return inicioModulo;
    }

    public void setInicioModulo(OffsetDateTime inicioModulo) {
        if(inicioModulo != null) {
            status = Status.EM_ANDAMENTO;
            this.inicioModulo = inicioModulo;
        }
    }

    public OffsetDateTime getFimModulo() {
        return fimModulo;
    }

    public void setFimModulo(OffsetDateTime fimModulo) {
        if(fimModulo != null) {
            status = Status.EM_FASE_AVALIACAO;
            this.fimModulo = inicioAvaliacao = fimModulo;
        }
    }

    public ModuloTrabalhador getModuloTrabalhador() {
        return moduloTrabalhador;
    }

    public void setModuloTrabalhador(ModuloTrabalhador moduloTrabalhador) {
        this.moduloTrabalhador = moduloTrabalhador;
    }


    @Override
    public String toString() {
        String textoHabilidades = habilidades.isEmpty() ? "Não há habilidades cadastradas!" : "";
        for(String h: habilidades) {
            textoHabilidades += "\n\t- "+h;
        }
        return "\nId: " + id +
                "\nNome: "+nome +
                ((tarefaValidacao == null || tarefaValidacao.isEmpty() || tarefaValidacao.isBlank()) ?
                        "" : "\nTarefa_validação: "+tarefaValidacao) +
                (status == null ? "" : "\nStatus: "+ status.getNome()) +
                "\nPrazo limite: "+prazo_limite +
                "\nInício da avaliação: "+ (inicioAvaliacao == null ?
                "ainda não iniciou" : inicioAvaliacao) +
                "\nNome da trilha: "+trilha.getNome() +
                "\nOcupação da trilha: "+trilha.getOcupacao() +
                "\nHabilidades: "+ textoHabilidades;
    }

}