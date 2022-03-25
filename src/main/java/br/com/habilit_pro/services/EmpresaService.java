package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.EmpresaDAO;
import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;

public class EmpresaService extends Service<Empresa, Long> {

    public EmpresaService(EntityManager entityManager) {
        super(entityManager, new EmpresaDAO(entityManager));
    }

}
