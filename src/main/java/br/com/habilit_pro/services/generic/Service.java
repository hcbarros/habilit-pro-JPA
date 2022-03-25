package br.com.habilit_pro.services.generic;

import br.com.habilit_pro.dao.generic.Dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class Service<T, I extends Serializable> {

    protected EntityManager entityManager;
    protected Dao<T,I> dao;
    private String nClass;
    public final Logger LOG;

    public Service(EntityManager entityManager, Dao<T,I> dao) {
        this.entityManager = entityManager;
        LOG = LogManager.getLogger(getClass());
        this.dao = dao;
        nClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0]).getSimpleName();
    }

    public T getById(I id) {
        LOG.info("Preparação para retornar entidade '"+nClass+"' pelo ID");
        try {
            getBeginTransaction();
            T t = dao.getById(id);
            commitAndCloseTransaction();
            return t;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public List<T> listAll() {
        try {
            LOG.info("Preparação para listar todas as entidades '"+nClass+"'");
            getBeginTransaction();
            List<T> list = dao.listAll();
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public List<T> listByName(String nome) {
        try {
            LOG.info("Preparação para listar todas as entidades '"+nClass+"' por nome!");
            getBeginTransaction();
            List<T> list = dao.listByName(nome);
            commitAndCloseTransaction();
            return list;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void create(T t) {
        LOG.info("Preparação para criação da entidade '"+nClass+"'");
        if(t == null) {
            LOG.error("A entidade '"+nClass+"' está nula!");
            throw new RuntimeException("Objeto '"+nClass+"' está nulo!");
        }
        try {
            getBeginTransaction();
            dao.create(t);
            commitAndCloseTransaction();
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
        LOG.info("Entidade '"+nClass+"' criada com sucesso!");
    }

    public T update(I id, T t) {
        LOG.info("Preparação para atualizar entidade "+nClass);
        if(t == null) {
            LOG.error("A entidade '"+nClass+"' está nula!");
            throw new RuntimeException("A entidade está nula!");
        }
        try {
            getBeginTransaction();
            T tAtual = dao.getById(id);
            if(tAtual == null) {
                throw new RuntimeException("Entidade '"+nClass+"' não encontrada!");
            }
            T response = dao.update(tAtual, t);
            commitAndCloseTransaction();
            LOG.info("Entidade '"+nClass+"' atualizada com sucesso!");
            return response;
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void delete(I id) {
        LOG.info("Preparação para deletar "+nClass);
        if(id == null) {
            LOG.error("O id informado está nulo!");
            throw new RuntimeException("O id está nulo!");
        }
        try {
            getBeginTransaction();
            T t = dao.getById(id);
            if(t == null) {
                throw new RuntimeException("Entidade '"+nClass+"' não encontrada!");
            }
            dao.delete(t);
            commitAndCloseTransaction();
            LOG.info("Entidade '"+nClass+"' excluída com sucesso!");
        }
        catch (Exception ex) {
            LOG.error("ERRO: "+ex.getMessage());
            throw new RuntimeException("Ocorreu o seguinte erro: "+ex.getMessage());
        }
    }

    public void getBeginTransaction() {
        entityManager.getTransaction().begin();
    }

    public void commitAndCloseTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
