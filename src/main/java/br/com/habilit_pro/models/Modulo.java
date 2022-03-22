package br.com.habilit_pro.models;

import br.com.habilit_pro.enums.Status;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Modulo {

    private Trilha trilha;
    private String nome;
    private Set<String> habilidades;
    private String tarefaValidacao;
    private Status status;
    private int prazo_limite = 10;
    private LocalDate inicioAvaliacao;

    public Modulo(Trilha trilha, String nome, Status status,
                  String tarefaValidacao, String ...habilidades) {

        this.trilha = trilha;
        this.nome = nome;
        definirStatus(status);
        setTarefaValidacao(tarefaValidacao);
        this.habilidades = new HashSet<>();
        addHabilidades(habilidades);
    }


    public Trilha getTrilha() {
        return trilha;
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
            int periodo = Period.between(inicioAvaliacao, LocalDate.now()).getDays();
            if(periodo >= prazo_limite) {
                this.status = Status.FINALIZADO;
            }
        }
        if(status != null) {
            if(status == Status.EM_FASE_AVALIACAO) {
                inicioAvaliacao = LocalDate.now();
            }
            else if(status == Status.EM_ANDAMENTO) {
                inicioAvaliacao = null;
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

    public LocalDate getInicioAvaliacao() {
        return inicioAvaliacao;
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