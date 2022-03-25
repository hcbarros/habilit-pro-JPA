package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.ModuloDAO;
import br.com.habilit_pro.enums.Status;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;


public class ModuloService extends Service<Modulo, Long> {

    public ModuloService(EntityManager entityManager) {
        super(entityManager, new ModuloDAO(entityManager));
    }


    public Modulo updateStatus(Long moduloId, Status status) {
        LOG.info("Preparação para atualizar status do módulo");
        if(moduloId == null) {
            LOG.error("O módulo informado está nulo!");
            throw new RuntimeException("O módulo está nulo!");
        }
        try {
            getBeginTransaction();
            Modulo modulo = dao.getById(moduloId);
            if(modulo == null) {
                throw new RuntimeException("Módulo não encontrado!");
            }
            Modulo response = ((ModuloDAO) dao).update(modulo, status);
            commitAndCloseTransaction();
            LOG.info("Status atualizado com sucesso!");
            return response;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }


}
