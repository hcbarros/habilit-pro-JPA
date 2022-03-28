package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.TrabalhadorDAO;
import br.com.habilit_pro.models.pessoa.trabalhador.ModuloTrabalhador;
import br.com.habilit_pro.models.pessoa.trabalhador.Trabalhador;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;


public class TrabalhadorService extends Service<Trabalhador, Long> {

    public TrabalhadorService(EntityManager entityManager) {
        super(entityManager, new TrabalhadorDAO(entityManager));
    }

    public Trabalhador addModuloTrabalhador(Long idTrabalhador, ModuloTrabalhador mt) {
        return updateModuloTrabalhador(idTrabalhador, mt, true);
    }

    public Trabalhador removeModuloTrabalhador(Long idTrabalhador, ModuloTrabalhador mt) {
        return updateModuloTrabalhador(idTrabalhador, mt, false);
    }

    private Trabalhador updateModuloTrabalhador(Long idTrabalhador, ModuloTrabalhador mt, boolean flag) {
        try {
            LOG.info("Preparação para atualizar trabalhador");
            getBeginTransaction();
            Trabalhador trabalhador = dao.getById(idTrabalhador);
            trabalhador.getModulosTrabalhador().forEach(m -> {
                if(flag && m.getModulo().getId() == mt.getModulo().getId()) {
                    throw new RuntimeException("O trabalhador informado já possui esse módulo em seu cadastro!");
                }
            });
            Trabalhador response = ((TrabalhadorDAO) dao).updateModuloTrabalhador(trabalhador, mt, flag);
            commitAndCloseTransaction();
            LOG.info("Trabalhador atualizado com sucesso!");
            return response;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }


}
