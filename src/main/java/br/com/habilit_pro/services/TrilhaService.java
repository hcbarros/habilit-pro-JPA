package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.TrabalhadorDAO;
import br.com.habilit_pro.dao.TrilhaDAO;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;

import java.util.List;

public class TrilhaService extends Service<Trilha, Long> {

    public TrilhaService(EntityManager entityManager) {
        super(entityManager, new TrilhaDAO(entityManager));
    }

    public List<Trilha> listTrilhasByTrabalhadorId(Long id) {
        try {
            LOG.info("Preparação para listar trilhas associadas a um trabalhador");
            getBeginTransaction();
            List<Trilha> trilhas = ((TrilhaDAO) dao).listTrilhasByTrabalhadorId(id);
            commitAndCloseTransaction();
            return trilhas;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

}
