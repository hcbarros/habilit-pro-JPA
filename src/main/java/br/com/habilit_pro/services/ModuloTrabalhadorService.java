package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.ModuloTrabalhadorDAO;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;

import javax.persistence.EntityManager;

public class ModuloTrabalhadorService extends Service<ModuloTrabalhador, Long> {

    private ModuloTrabalhadorService() { }

    public ModuloTrabalhadorService(EntityManager entityManager) {
        super(entityManager, ModuloTrabalhador.class, new ModuloTrabalhadorDAO(entityManager));
    }

}