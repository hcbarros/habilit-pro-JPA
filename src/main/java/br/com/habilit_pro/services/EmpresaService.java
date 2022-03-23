package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.EmpresaDAO;
import br.com.habilit_pro.models.Empresa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class EmpresaService {

    private EntityManager entityManager;
    private EmpresaDAO empresaDAO;
    private final Logger LOG = LogManager.getLogger(EmpresaService.class);


    public EmpresaService(EntityManager entityManager) {
        this.entityManager = entityManager;
        empresaDAO = new EmpresaDAO(this.entityManager);
    }


    public Empresa getById(Long id) {
        getBeginTransaction();
        Empresa empresa =  empresaDAO.getById(id);
        commitAndCloseTransaction();
        if(empresa == null) {
            throw new RuntimeException("Empresa não encontrada!");
        }
        return empresa;
    }

    public List<Empresa> listAll() {
        try {
            LOG.info("Preparação para listar todas as empresas");
            getBeginTransaction();
            List<Empresa> list = empresaDAO.listAll();
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public List<Empresa> listByName(String nome) {
        try {
            LOG.info("Preparação para listar empresas por nome");
            getBeginTransaction();
            List<Empresa> list = empresaDAO.listByName(nome);
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void create(Empresa empresa) {
        LOG.info("Preparação para criação da empresa");
        if(empresa == null) {
            LOG.error("A empresa informada está nula!");
            throw new RuntimeException("A empresa está nula!");
        }
        try {
            getBeginTransaction();
            empresaDAO.create(empresa);
            commitAndCloseTransaction();
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
        LOG.info("Empresa criada com sucesso!");
    }

    public Empresa update(Long id, Empresa empresa) {
        LOG.info("Preparação para atualizar empresa");
        if(empresa == null) {
            LOG.error("A empresa informada está nula!");
            throw new RuntimeException("A empresa está nula!");
        }
        try {
            getBeginTransaction();
            Empresa empresaAtual = empresaDAO.getById(id);
            if(empresaAtual == null) {
                throw new RuntimeException("Empresa não encontrada!");
            }
            Empresa response = empresaDAO.update(empresaAtual, empresa);
            commitAndCloseTransaction();
            LOG.info("Empresa atualizada com sucesso!");
            return response;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void delete(Long id) {
        LOG.info("Preparação para deletar a empresa");
        if(id == null) {
            LOG.error("O id informado está nulo!");
            throw new RuntimeException("O id está nulo!");
        }
        try {
            getBeginTransaction();
            Empresa empresa = empresaDAO.getById(id);
            if(empresa == null) {
                throw new RuntimeException("Empresa não encontrada!");
            }
            empresaDAO.delete(empresa);
            commitAndCloseTransaction();
            LOG.info("Empresa excluída com sucesso!");
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
