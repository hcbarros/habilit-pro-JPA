package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.models.pessoa.Usuario;

import javax.persistence.EntityManager;

public class UsuarioDAO extends Dao<Usuario, Long> {

    public UsuarioDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Usuario update(Usuario usuarioAtual, Usuario usuarioFuturo) {
        usuarioAtual.setNome(usuarioFuturo.getNome());
        usuarioAtual.setEmail(usuarioFuturo.getEmail());
        usuarioAtual.setSenha(usuarioFuturo.getSenha());
        usuarioAtual.setPerfis(usuarioFuturo.getPerfis());
        return entityManager.merge(usuarioAtual);
    }

}
