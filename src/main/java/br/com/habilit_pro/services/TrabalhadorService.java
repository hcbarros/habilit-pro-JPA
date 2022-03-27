package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.TrabalhadorDAO;
import br.com.habilit_pro.models.Trilha;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;
import java.util.List;

public class TrabalhadorService extends Service<Trabalhador, Long> {

    public TrabalhadorService(EntityManager entityManager) {
        super(entityManager, new TrabalhadorDAO(entityManager));
    }


    public List<Trilha> listTrilhasByTrabalhadorId(Long id) {
        try {
            LOG.info("Preparação para listar trilhas de um trabalhador");
            getBeginTransaction();
            List<Trilha> list = ((TrabalhadorDAO) dao).listTrilhasByTrabalhadorId(id);
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

}
