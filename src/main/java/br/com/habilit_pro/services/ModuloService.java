package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.ModuloDAO;
import br.com.habilit_pro.enums.Status;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.services.generic.Service;

import javax.persistence.EntityManager;
import java.time.OffsetDateTime;

public class ModuloService extends Service<Modulo, Long> {

    public ModuloService(EntityManager entityManager) {
        super(entityManager, new ModuloDAO(entityManager));
    }


    public Modulo iniciarModulo(Long moduloId, OffsetDateTime inicio) {
        return updateAll(moduloId, null, inicio,true,"Módulo iniciado com sucesso!");
    }

    public Modulo finalizarModulo(Long moduloId, OffsetDateTime fim) {
        return updateAll(moduloId, null, fim,false,"Módulo finalizado com sucesso!");
    }

    public Modulo updateStatus(Long moduloId, Status status) {
        return updateAll(moduloId, status,null,false,"Status atualizado com sucesso!");
    }

    public Modulo addHabilidades(Long moduloId, String ...habilidades) {
        return updateAll(moduloId,null,null,true, "Habilidades adicionadas com sucesso!",
                habilidades);
    }

    public Modulo removeHabilidades(Long moduloId, String ...habilidades) {
        return updateAll(moduloId,null,null,false, "Habilidades adicionadas com sucesso!",
                habilidades);
    }


    private Modulo updateAll(Long moduloId, Status status, OffsetDateTime data,
                             boolean flag, String texto, String ...habilidades) {
        try {
            LOG.info("Preparação para atualizar dados de módulo");
            getBeginTransaction();
            Modulo modulo = dao.getById(moduloId);
            Modulo response = null;
            ModuloDAO moduloDAO = (ModuloDAO) dao;
            if(status != null) {
                response = ((ModuloDAO) dao).updateStatus(modulo, status);
            }
            else if(data != null) {
                response = ((ModuloDAO) dao).updateData(modulo, flag, data);
            }
            else if(habilidades != null) {
                response = ((ModuloDAO) dao).updateHabilidades(modulo, flag, habilidades);
            }
            else {
                throw new RuntimeException("Não foi possível atualizar o módulo!");
            }
            commitAndCloseTransaction();
            LOG.info(texto);
            return response;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }


}
