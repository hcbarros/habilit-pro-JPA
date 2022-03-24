package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.EmpresaDAO;
import br.com.habilit_pro.models.Empresa;

import javax.persistence.EntityManager;


public class EmpresaService extends Service<Empresa, Long>{

    private EmpresaService() { }

    public EmpresaService(EntityManager entityManager) {
        super(entityManager, Empresa.class, new EmpresaDAO(entityManager));
    }

}
