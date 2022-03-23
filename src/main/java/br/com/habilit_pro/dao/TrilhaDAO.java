package br.com.habilit_pro.dao;

import br.com.habilit_pro.models.Trilha;

import javax.persistence.EntityManager;
import java.util.List;

public class TrilhaDAO {

    private EntityManager entityManager;

    public TrilhaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Trilha getById(Long id) {
        return entityManager.find(Trilha.class, id);
    }

    public List<Trilha> listAll() {
        return entityManager.createQuery("select t from Trilha t",
                Trilha.class).getResultList();
    }

    public List<Trilha> listByName(String nome) {
        return entityManager.createQuery(
                        "select t from Trilha t where lower(t.nome) = :nome", Trilha.class)
                .setParameter("nome", nome.toLowerCase())
                .getResultList();
    }

    private long countByOcupacaoAndNomeEmpresa(String ocupacao, String nomeEmpresa) {
        Long query = entityManager.createQuery(
              "select count(t) from Trilha t where lower(t.ocupacao) = :ocupacao and lower(t.empresa.nome) = :nomeEmpresa",
                        Long.class)
                .setParameter("ocupacao", ocupacao.toLowerCase())
                .setParameter("nomeEmpresa", nomeEmpresa.toLowerCase())
                .getSingleResult();
        return query == null ? 0 : query;
    }

    public void create(Trilha trilha) {
        long count = countByOcupacaoAndNomeEmpresa(
                trilha.getOcupacao(), trilha.getEmpresa().getNome());
        trilha.setNome(count);
        trilha.setApelido(count);
        entityManager.persist(trilha);
    }

    public Trilha update(Trilha trilhaAtual, Trilha trilhaFutura) {
        trilhaAtual.setAnotacoes(trilhaFutura.getAnotacoes());
        trilhaAtual.setSatisfacao(trilhaFutura.getSatisfacao());
        if(!trilhaAtual.getEmpresa().getNome().equalsIgnoreCase(trilhaFutura.getEmpresa().getNome()) ||
                            !trilhaAtual.getOcupacao().equalsIgnoreCase(trilhaFutura.getOcupacao())) {
            long count = countByOcupacaoAndNomeEmpresa(
                    trilhaFutura.getOcupacao(), trilhaFutura.getEmpresa().getNome());
            trilhaAtual.setEmpresa(trilhaFutura.getEmpresa());
            trilhaAtual.setOcupacao(trilhaFutura.getOcupacao());
            trilhaAtual.setNome(count);
            trilhaAtual.setApelido(count);
        }
        return entityManager.merge(trilhaAtual);
    }

    public void delete(Trilha trilha) {
        entityManager.merge(trilha);
        entityManager.remove(trilha);
    }

}
