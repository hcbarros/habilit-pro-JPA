package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.Dao;
import br.com.habilit_pro.models.Trilha;

import javax.persistence.*;


public class TrilhaDAO extends Dao<Trilha, Long> {

    private TrilhaDAO() { }

    public TrilhaDAO(EntityManager entityManager) {
        super(entityManager, Trilha.class);
    }

    private long countByOcupacaoAndNomeEmpresa(Trilha trilha) {
        Long query = entityManager.createQuery(
              "select count(t) from Trilha t where lower(t.ocupacao) = :ocupacao and lower(t.empresa.nome) = :nomeEmpresa",
                        Long.class)
                .setParameter("ocupacao", trilha.getOcupacao().toLowerCase())
                .setParameter("nomeEmpresa", trilha.getEmpresa().getNome().toLowerCase())
                .getSingleResult();
        return query == null ? 0 : query;
    }

    public void create(Trilha trilha) {
        long count = countByOcupacaoAndNomeEmpresa(trilha);
        trilha.setNome(count);
        trilha.setApelido(count);
        entityManager.persist(trilha);
    }

    public Trilha update(Trilha trilhaAtual, Trilha trilhaFutura) {
        trilhaAtual.setAnotacoes(trilhaFutura.getAnotacoes());
        trilhaAtual.setSatisfacao(trilhaFutura.getSatisfacao());
        if(!trilhaAtual.getEmpresa().getNome().equalsIgnoreCase(trilhaFutura.getEmpresa().getNome()) ||
                            !trilhaAtual.getOcupacao().equalsIgnoreCase(trilhaFutura.getOcupacao())) {
            long count = countByOcupacaoAndNomeEmpresa(trilhaFutura);
            trilhaAtual.setEmpresa(trilhaFutura.getEmpresa());
            trilhaAtual.setOcupacao(trilhaFutura.getOcupacao());
            trilhaAtual.setNome(count);
            trilhaAtual.setApelido(count);
        }
        return entityManager.merge(trilhaAtual);
    }



}
