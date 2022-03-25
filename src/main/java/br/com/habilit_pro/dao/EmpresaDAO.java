package br.com.habilit_pro.dao;

import br.com.habilit_pro.dao.generic.Dao;
import br.com.habilit_pro.models.Empresa;

import javax.persistence.EntityManager;


public class EmpresaDAO extends Dao<Empresa, Long> {

    public EmpresaDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Empresa update(Empresa empresaAtual, Empresa empresaFutura) {
        empresaAtual.setNome(empresaFutura.getNome());
        empresaAtual.setTipo(empresaFutura.getTipo());
        empresaAtual.setNomeFilial(empresaFutura.getNomeFilial());
        empresaAtual.setRegional(empresaFutura.getRegional());
        empresaAtual.setSegmento(empresaFutura.getSegmento());
        empresaAtual.setCidade(empresaFutura.getCidade());
        empresaAtual.setEstado(empresaFutura.getEstado());
        return entityManager.merge(empresaAtual);
    }


}
