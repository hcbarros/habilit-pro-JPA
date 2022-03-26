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
        try {
            Modulo modulo = getById(moduloId);
            getBeginTransaction();
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

    public Modulo updateHabilidades(Long moduloId, boolean add, String ...habilidades) {
        try {
            Modulo modulo = getById(moduloId);
            getBeginTransaction();
            Modulo response = ((ModuloDAO) dao).update(modulo, add, habilidades);
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
