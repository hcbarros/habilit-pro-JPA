package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.UsuarioDAO;
import br.com.habilit_pro.models.pessoa.Usuario;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;

public class UsuarioService extends Service<Usuario, Long> {

    public UsuarioService(EntityManager entityManager) {
        super(entityManager, new UsuarioDAO(entityManager));
    }

}
