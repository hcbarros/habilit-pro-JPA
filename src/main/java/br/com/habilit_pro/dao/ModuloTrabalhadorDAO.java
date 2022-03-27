package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;

import javax.persistence.EntityManager;

public class ModuloTrabalhadorDAO extends Dao<ModuloTrabalhador, Long> {

    public ModuloTrabalhadorDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public ModuloTrabalhador update(ModuloTrabalhador mt1, ModuloTrabalhador mt2) {
        return null;
    }

}