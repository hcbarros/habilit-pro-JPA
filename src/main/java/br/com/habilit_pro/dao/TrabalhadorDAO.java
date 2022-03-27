package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;

import javax.persistence.EntityManager;

public class TrabalhadorDAO extends Dao<Trabalhador, Long> {

    public TrabalhadorDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Trabalhador update(Trabalhador trabalhadorAtual, Trabalhador trabalhadorFuturo) {
        trabalhadorAtual.setEmpresa(trabalhadorFuturo.getEmpresa());
        trabalhadorAtual.setNome(trabalhadorFuturo.getNome());
        trabalhadorAtual.setFuncao(trabalhadorFuturo.getFuncao());
        trabalhadorAtual.setSetor(trabalhadorFuturo.getSetor());
        return entityManager.merge(trabalhadorAtual);
    }

    public Trabalhador updateModuloTrabalhador(Trabalhador trabalhador,
                                        ModuloTrabalhador mt, boolean add) {
        if(add) {
            trabalhador.addModuloTrabalhador(mt.getModulo(), mt.getAvaliacao(), mt.getAnotacao());
        }
        else {
            trabalhador.removeModuloTrabalhador(mt.getId());
        }
        return entityManager.merge(trabalhador);
    }


}
