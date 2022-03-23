package br.com.habilit_pro.dao;

import br.com.habilit_pro.enums.Status;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.models.Trilha;

import javax.persistence.EntityManager;
import java.util.List;

public class ModuloDAO {

    private EntityManager entityManager;

    public ModuloDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Modulo getById(Long id) {
        return entityManager.find(Modulo.class, id);
    }

    public List<Modulo> listAll() {
        return entityManager.createQuery("select m from Modulo m",
                Modulo.class).getResultList();
    }

    public List<Modulo> listByName(String nome) {
        return entityManager.createQuery(
                        "select m from Modulo m where lower(m.nome) = :nome", Modulo.class)
                .setParameter("nome", nome.toLowerCase())
                .getResultList();
    }

    public void create(Modulo trilha) {
        entityManager.persist(trilha);
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

    public void delete(Modulo modulo) {
        entityManager.merge(modulo);
        entityManager.remove(modulo);
    }

}
