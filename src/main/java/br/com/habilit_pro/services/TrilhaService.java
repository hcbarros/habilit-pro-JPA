package br.com.habilit_pro.services;

import br.com.habilit_pro.dao.EmpresaDAO;
import br.com.habilit_pro.dao.TrilhaDAO;
import br.com.habilit_pro.models.Empresa;
import br.com.habilit_pro.models.Trilha;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class TrilhaService {

    private EntityManager entityManager;
    private TrilhaDAO trilhaDAO;
    private final Logger LOG = LogManager.getLogger(TrilhaService.class);


    public TrilhaService(EntityManager entityManager) {
        this.entityManager = entityManager;
        trilhaDAO = new TrilhaDAO(this.entityManager);
    }


    public Trilha getById(Long id) {
        getBeginTransaction();
        Trilha trilha =  trilhaDAO.getById(id);
        commitAndCloseTransaction();
        if(trilha == null) {
            throw new RuntimeException("Trilha não encontrada!");
        }
        return trilha;
    }

    public List<Trilha> listAll() {
        try {
            LOG.info("Preparação para listar todas as trilhas");
            getBeginTransaction();
            List<Trilha> list = trilhaDAO.listAll();
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public List<Trilha> listByName(String nome) {
        try {
            LOG.info("Preparação para listar trilhas por nome");
            getBeginTransaction();
            List<Trilha> list = trilhaDAO.listByName(nome);
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void create(Trilha trilha) {
        LOG.info("Preparação para criação da trilha");
        if(trilha == null) {
            LOG.error("A trilha informada está nula!");
            throw new RuntimeException("O trilha está nula!");
        }
        try {
            getBeginTransaction();
            trilhaDAO.create(trilha);
            commitAndCloseTransaction();
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
        LOG.info("Trilha criada com sucesso!");
    }

    public Trilha update(Long id, Trilha trilha) {
        LOG.info("Preparação para atualizar trilha");
        if(trilha == null) {
            LOG.error("A trilha informada está nula!");
            throw new RuntimeException("A trilha está nula!");
        }
        try {
            getBeginTransaction();
            Trilha trilhaAtual = trilhaDAO.getById(id);
            if(trilhaAtual == null) {
                throw new RuntimeException("Trilha não encontrada!");
            }
            Trilha response = trilhaDAO.update(trilhaAtual, trilha);
            commitAndCloseTransaction();
            LOG.info("Trilha atualizada com sucesso!");
            return response;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void delete(Long id) {
        LOG.info("Preparação para deletar a trilha");
        if(id == null) {
            LOG.error("O id informado está nulo!");
            throw new RuntimeException("O id está nulo!");
        }
        try {
            getBeginTransaction();
            Trilha trilha = trilhaDAO.getById(id);
            if(trilha == null) {
                throw new RuntimeException("Trilha não encontrada!");
            }
            trilhaDAO.delete(trilha);
            commitAndCloseTransaction();
            LOG.info("Trilha excluída com sucesso!");
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
