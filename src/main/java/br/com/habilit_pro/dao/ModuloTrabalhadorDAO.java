package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;

import javax.persistence.EntityManager;


public class ModuloTrabalhadorDAO extends Dao<ModuloTrabalhador, Long> {

    public ModuloTrabalhadorDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public ModuloTrabalhador update(ModuloTrabalhador t1, ModuloTrabalhador t2) {
        return null;
    }

}