package br.com.habilit_pro.dao;

import br.com.habilit_pro.models.pessoa.Usuario;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;

import javax.persistence.EntityManager;

public class TrabalhadorDAO extends Dao<Trabalhador, Long>{

    private TrabalhadorDAO() { }

    public TrabalhadorDAO(EntityManager entityManager) {
        super(entityManager, Trabalhador.class);
    }

    public Trabalhador update(Trabalhador trabalhadorAtual, Trabalhador trabalhadorFuturo) {
        trabalhadorAtual.setEmpresa(trabalhadorFuturo.getEmpresa());
        trabalhadorAtual.setNome(trabalhadorFuturo.getNome());
        trabalhadorAtual.setFuncao(trabalhadorFuturo.getFuncao());
        trabalhadorAtual.setSetor(trabalhadorFuturo.getSetor());
        trabalhadorAtual.setModulosTrabalhador(trabalhadorFuturo.getModulosTrabalhador());
        return entityManager.merge(trabalhadorAtual);
    }

}
