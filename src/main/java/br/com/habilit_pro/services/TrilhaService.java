package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.TrilhaDAO;
import br.com.habilit_pro.models.Trilha;

import javax.persistence.EntityManager;


public class TrilhaService extends Service<Trilha, Long>{

    private TrilhaService() { }

    public TrilhaService(EntityManager entityManager) {
        super(entityManager, Trilha.class, new TrilhaDAO(entityManager));
    }

}
