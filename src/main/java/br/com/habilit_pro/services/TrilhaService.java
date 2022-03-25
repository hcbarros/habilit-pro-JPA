package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.TrilhaDAO;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;

public class TrilhaService extends Service<Trilha, Long> {

    public TrilhaService(EntityManager entityManager) {
        super(entityManager, new TrilhaDAO(entityManager));
    }

}
