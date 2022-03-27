package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;

import javax.persistence.EntityManager;
import java.util.List;

public class TrabalhadorDAO extends Dao<Trabalhador, Long> {

    public TrabalhadorDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Trabalhador update(Trabalhador trabalhadorAtual, Trabalhador trabalhadorFuturo) {
        trabalhadorAtual.setEmpresa(trabalhadorFuturo.getEmpresa());
        trabalhadorAtual.setNome(trabalhadorFuturo.getNome());
        trabalhadorAtual.setFuncao(trabalhadorFuturo.getFuncao());
        trabalhadorAtual.setSetor(trabalhadorFuturo.getSetor());
        trabalhadorAtual.setModulosTrabalhador(trabalhadorFuturo.getModulosTrabalhador());
        return entityManager.merge(trabalhadorAtual);
    }

    public List<Trilha> listTrilhasByTrabalhadorId(Long id) {
        return entityManager.createQuery(
                "select distinct tr from ModuloTrabalhador mt inner join " +
                        "Modulo m on m.trilha.id = mt.modulo.trilha.id inner join " +
                        "Trilha tr on tr.id = m.trilha.id where mt.trabalhador.id = :id", Trilha.class)
                .setParameter("id", id)
                .getResultList();
    }

}
