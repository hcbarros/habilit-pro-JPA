package br.com.habilit_pro.dao.generic;

import javax.persistence.EntityManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class Dao<T, I extends Serializable> {

    protected EntityManager entityManager;
    private Class<T> classe;

    public Dao(EntityManager entityManager) {
        this.entityManager = entityManager;
        classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public T getById(I id) {
        return entityManager.find(classe, id);
    }

    public List<T> listAll() {
        return entityManager.createQuery("select t from "+classe.getSimpleName()+" t",
                classe).getResultList();
    }

    public List<T> listByName(String nome) {
        return entityManager.createQuery(
                        "select t from "+classe.getSimpleName()+" t where lower(t.nome) = :nome", classe)
                .setParameter("nome", nome.toLowerCase())
                .getResultList();
    }

    public void create(T t) {
        entityManager.persist(t);
    }

    public abstract T update(T t1, T t2);

    public void delete(T t) {
        entityManager.merge(t);
        entityManager.remove(t);
    }

}
