package br.com.habilit_pro.dao;

import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;

import javax.persistence.EntityManager;


public class ModuloTrabalhadorDAO extends Dao<ModuloTrabalhador, Long> {

    private ModuloTrabalhadorDAO() { }

    public ModuloTrabalhadorDAO(EntityManager entityManager) {
        super(entityManager, ModuloTrabalhador.class);
    }

    @Override
    public ModuloTrabalhador update(ModuloTrabalhador t1, ModuloTrabalhador t2) {
        return null;
    }

}