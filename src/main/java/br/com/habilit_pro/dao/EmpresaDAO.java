package br.com.habilit_pro.dao;

import br.com.habilit_pro.models.Empresa;

import javax.persistence.EntityManager;
import java.util.List;

public class EmpresaDAO {

    private EntityManager entityManager;

    public EmpresaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Empresa getById(Long id) {
        return entityManager.find(Empresa.class, id);
    }

    public List<Empresa> listAll() {
        return entityManager.createQuery("select e from Empresa e",
                Empresa.class).getResultList();
    }

    public List<Empresa> listByName(String nome) {
        return entityManager.createQuery(
                        "select e from Empresa e where lower(e.nome) = :nome", Empresa.class)
                .setParameter("nome", nome.toLowerCase())
                .getResultList();
    }

    public void create(Empresa empresa) {
        entityManager.persist(empresa);
    }

    public Empresa update(Empresa empresaAtual, Empresa empresaFutura) {
        empresaAtual.setNome(empresaFutura.getNome());
        empresaAtual.setTipo(empresaFutura.getTipo());
        empresaAtual.setNomeFilial(empresaFutura.getNomeFilial());
        empresaAtual.setCnpj(empresaFutura.getCnpj());
        empresaAtual.setRegional(empresaFutura.getRegional());
        empresaAtual.setSegmento(empresaFutura.getSegmento());
        empresaAtual.setCnpj(empresaFutura.getCnpj());
        empresaAtual.setCidade(empresaFutura.getCidade());
        empresaAtual.setEstado(empresaFutura.getEstado());
        return entityManager.merge(empresaAtual);
    }

    public void delete(Empresa empresa) {
        entityManager.merge(empresa);
        entityManager.remove(empresa);
    }

}
