package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.TrabalhadorDAO;
import br.com.habilit_pro.dao.UsuarioDAO;
import br.com.habilit_pro.models.pessoa.Usuario;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;

import javax.persistence.EntityManager;

public class TrabalhadorService extends Service<Trabalhador, Long> {

    private TrabalhadorService() { }

    public TrabalhadorService(EntityManager entityManager) {
        super(entityManager, Trabalhador.class, new TrabalhadorDAO(entityManager));
    }

}
