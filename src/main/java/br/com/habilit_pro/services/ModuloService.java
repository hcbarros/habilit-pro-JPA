package br.com.habilit_pro.services;


import br.com.habilit_pro.dao.ModuloDAO;
import br.com.habilit_pro.dao.TrilhaDAO;
import br.com.habilit_pro.enums.Status;
import br.com.habilit_pro.models.Modulo;
import br.com.habilit_pro.models.Trilha;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class ModuloService {

    private EntityManager entityManager;
    private ModuloDAO moduloDAO;
    private final Logger LOG = LogManager.getLogger(ModuloService.class);


    public ModuloService(EntityManager entityManager) {
        this.entityManager = entityManager;
        moduloDAO = new ModuloDAO(this.entityManager);
    }


    public Modulo getById(Long id) {
        getBeginTransaction();
        Modulo modulo =  moduloDAO.getById(id);
        commitAndCloseTransaction();
        if(modulo == null) {
            LOG.error("ERRO: módulo não encontrado!");
            throw new RuntimeException("Módulo não encontrada!");
        }
        return modulo;
    }

    public List<Modulo> listAll() {
        try {
            LOG.info("Preparação para listar todos os módulos");
            getBeginTransaction();
            List<Modulo> list = moduloDAO.listAll();
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public List<Modulo> listByName(String nome) {
        try {
            LOG.info("Preparação para listar módulos por nome");
            getBeginTransaction();
            List<Modulo> list = moduloDAO.listByName(nome);
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void create(Modulo modulo) {
        LOG.info("Preparação para criação do módulo");
        if(modulo == null) {
            LOG.error("O módulo informado está nulo!");
            throw new RuntimeException("O módulo está nulo!");
        }
        try {
            getBeginTransaction();
            moduloDAO.create(modulo);
            commitAndCloseTransaction();
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
        LOG.info("Módulo criado com sucesso!");
    }

    public Modulo update(Long id, Modulo modulo) {
        LOG.info("Preparação para atualizar módulo");
        if(modulo == null) {
            LOG.error("O módulo informado está nulo!");
            throw new RuntimeException("O módulo está nulo!");
        }
        try {
            getBeginTransaction();
            Modulo moduloAtual = moduloDAO.getById(id);
            if(moduloAtual == null) {
                throw new RuntimeException("Módulo não encontrado!");
            }
            Modulo response = moduloDAO.update(moduloAtual, modulo);
            commitAndCloseTransaction();
            LOG.info("Módulo atualizado com sucesso!");
            return response;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public Modulo updateStatus(Long moduloId, Status status) {
        LOG.info("Preparação para atualizar status do módulo");
        if(moduloId == null) {
            LOG.error("O módulo informado está nulo!");
            throw new RuntimeException("O módulo está nulo!");
        }
        try {
            getBeginTransaction();
            Modulo modulo = moduloDAO.getById(moduloId);
            if(modulo == null) {
                throw new RuntimeException("Módulo não encontrado!");
            }
            Modulo response = moduloDAO.update(modulo, status);
            commitAndCloseTransaction();
            LOG.info("Status atualizado com sucesso!");
            return response;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void delete(Long id) {
        LOG.info("Preparação para deletar o módulo");
        if(id == null) {
            LOG.error("O id informado está nulo!");
            throw new RuntimeException("O id está nulo!");
        }
        try {
            getBeginTransaction();
            Modulo modulo = moduloDAO.getById(id);
            if(modulo == null) {
                throw new RuntimeException("Módulo não encontrado!");
            }
            moduloDAO.delete(modulo);
            commitAndCloseTransaction();
            LOG.info("Módulo excluído com sucesso!");
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    private void getBeginTransaction() {
        entityManager.getTransaction().begin();
    }

    private void commitAndCloseTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
