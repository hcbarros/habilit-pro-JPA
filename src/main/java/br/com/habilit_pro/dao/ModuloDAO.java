package br.com.habilit_pro.dao;

import br.com.habilit_pro.enums.Status;
import br.com.habilit_pro.models.Modulo;

import javax.persistence.EntityManager;


public class ModuloDAO extends Dao<Modulo, Long>{

    private ModuloDAO() { }

    public ModuloDAO(EntityManager entityManager) {
        super(entityManager, Modulo.class);
    }

    public Modulo update(Modulo moduloAtual, Modulo moduloFuturo) {
        moduloAtual.setNome(moduloFuturo.getNome());
        moduloAtual.setPrazo_limite(moduloFuturo.getPrazo_limite());
        moduloAtual.setTarefaValidacao(moduloFuturo.getTarefaValidacao());
        moduloAtual.setTrilha(moduloFuturo.getTrilha());
        moduloAtual.setTarefaValidacao(moduloFuturo.getTarefaValidacao());
        String[] habs = new String[moduloFuturo.getHabilidades().size()];
        moduloAtual.addHabilidades(moduloFuturo.getHabilidades().toArray(habs));
        return entityManager.merge(moduloAtual);
    }

    public Modulo update(Modulo modulo, Status status) {
        modulo.definirStatus(status);
        return entityManager.merge(modulo);
    }

}
