package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.TrabalhadorDAO;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;

public class TrabalhadorService extends Service<Trabalhador, Long> {

    public TrabalhadorService(EntityManager entityManager) {
        super(entityManager, new TrabalhadorDAO(entityManager));
    }

}
