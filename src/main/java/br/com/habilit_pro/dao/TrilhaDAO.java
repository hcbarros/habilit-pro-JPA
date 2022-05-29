package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.models.Trilha;

import javax.persistence.*;
import java.util.List;


public class TrilhaDAO extends Dao<Trilha, Long> {

    public TrilhaDAO(EntityManager entityManager) {
        super(entityManager);
    }


    public Trilha update(Trilha trilhaAtual, Trilha trilhaFutura) {
        trilhaAtual.setAnotacoes(trilhaFutura.getAnotacoes());
        trilhaAtual.setSatisfacao(trilhaFutura.getSatisfacao());
        if(!trilhaAtual.getEmpresa().getNome().equalsIgnoreCase(trilhaFutura.getEmpresa().getNome()) ||
                            !trilhaAtual.getOcupacao().equalsIgnoreCase(trilhaFutura.getOcupacao())) {
            trilhaAtual.setEmpresa(trilhaFutura.getEmpresa());
            trilhaAtual.setOcupacao(trilhaFutura.getOcupacao());
            trilhaAtual.setNome(null);
            trilhaAtual.setApelido(null);
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
