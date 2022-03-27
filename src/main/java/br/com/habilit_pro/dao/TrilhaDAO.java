package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.models.Trilha;

import javax.persistence.*;
import java.util.List;


public class TrilhaDAO extends Dao<Trilha, Long> {

    public TrilhaDAO(EntityManager entityManager) {
        super(entityManager);
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
        trilha.definirNomes(count);
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
            trilhaAtual.definirNomes(count);
        }
        return entityManager.merge(trilhaAtual);
    }

    public List<Trilha> listTrilhasByTrabalhadorId(Long id) {
        return entityManager.createQuery(
                        "select distinct mt.modulo.trilha from ModuloTrabalhador mt inner join " +
                                "Trabalhador t on t.cpf = mt.cpf where t.id = :id", Trilha.class)
                .setParameter("id", id)
                .getResultList();
    }

}
