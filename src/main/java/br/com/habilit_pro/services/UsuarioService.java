package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.UsuarioDAO;
import br.com.habilit_pro.models.pessoa.Usuario;

import javax.persistence.EntityManager;

public class UsuarioService extends Service<Usuario, Long>{

    private UsuarioService() { }

    public UsuarioService(EntityManager entityManager) {
        super(entityManager, Usuario.class, new UsuarioDAO(entityManager));
    }

}
