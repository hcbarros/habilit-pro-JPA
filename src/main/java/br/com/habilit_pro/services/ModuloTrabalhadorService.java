package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.ModuloTrabalhadorDAO;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;

public class ModuloTrabalhadorService extends Service<ModuloTrabalhador, Long> {

    public ModuloTrabalhadorService(EntityManager entityManager) {
        super(entityManager, new ModuloTrabalhadorDAO(entityManager));
    }

}