package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.enums.Status;
import br.com.habilit_pro.models.Modulo;

import javax.persistence.EntityManager;


public class ModuloDAO extends Dao<Modulo, Long> {

    public ModuloDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Modulo update(Modulo moduloAtual, Modulo moduloFuturo) {
        moduloAtual.setNome(moduloFuturo.getNome());
        moduloAtual.setPrazo_limite(moduloFuturo.getPrazo_limite());
        moduloAtual.setTrilha(moduloFuturo.getTrilha());
        moduloAtual.setTarefaValidacao(moduloFuturo.getTarefaValidacao());
        return entityManager.merge(moduloAtual);
    }

    public Modulo update(Modulo modulo, Status status) {
        modulo.definirStatus(status);
        return entityManager.merge(modulo);
    }

    public Modulo update(Modulo modulo, boolean add, String ...habilidades) {
        modulo.definirHabilidades(add, habilidades);
        return entityManager.merge(modulo);
    }

}
