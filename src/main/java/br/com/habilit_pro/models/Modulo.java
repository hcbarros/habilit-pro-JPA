package br.com.habilit_pro.models;

import br.com.habilit_pro.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A nome do módulo não deve ser nulo!")
    @Pattern(regexp = "(?=.*[a-zA-Z]).[0-9a-zA-Z$*&@#_\\-.\\wÀ-ú ]+",
            message="Informe o nome do módulo!")
    private String nome;

    @ElementCollection
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


    public Modulo() {
        habilidades = new HashSet<>();
    }

    public Modulo(Trilha trilha, String nome, String tarefaValidacao, String ...habilidades) {
        this.trilha = trilha;
        this.nome = nome;
        setTarefaValidacao(tarefaValidacao);
        this.habilidades = new HashSet<>();
        addHabilidades(habilidades);
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

    public void definirStatus(Status status) {
        if(inicioAvaliacao != null) {
            int periodo = Period.between(inicioAvaliacao.toLocalDate(), LocalDate.now()).getDays();
            if(periodo >= prazo_limite) {
                this.status = Status.FINALIZADO;
            }
        }
        if(status != null) {
            if(status == Status.EM_FASE_AVALIACAO) {
                inicioAvaliacao = OffsetDateTime.now();
                fimModulo = OffsetDateTime.now();
            }
            else if(status == Status.EM_ANDAMENTO) {
                inicioAvaliacao = null;
                inicioModulo = OffsetDateTime.now();
            }
            this.status = status;
        }
    }

    public String getTarefaValidacao() {
        return tarefaValidacao;
    }

    public void setTarefaValidacao(String tarefaValidacao) {
        if(tarefaValidacao != null) {
            this.tarefaValidacao = tarefaValidacao;
        }
    }

    public Set<String> getHabilidades() {
        return habilidades;
    }

    public void addHabilidades(String ...habilidades) {
        if(habilidades != null) {
            Arrays.asList(habilidades).forEach(h -> {
                if(h != null && !this.habilidades.contains(h.toUpperCase())) {
                    this.habilidades.add(h.toUpperCase());
                }
            });
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
        this.inicioModulo = inicioModulo;
    }

    public OffsetDateTime getFimModulo() {
        return fimModulo;
    }

    public void setFimModulo(OffsetDateTime fimModulo) {
        this.fimModulo = fimModulo;
    }

    @Override
    public String toString() {
        String textoHabilidades = habilidades.isEmpty() ? "Não há habilidades cadastradas!" : "";
        for(String h: habilidades) {
            textoHabilidades += "\n\t- "+h;
        }
        return "\nNome: "+nome +
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